package org.example.elasticsearch.service.impl;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.elasticsearch.action.bulk.BulkRequest;
import org.elasticsearch.action.bulk.BulkResponse;
import org.elasticsearch.action.index.IndexRequest;
import org.elasticsearch.action.search.SearchRequest;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.common.text.Text;
import org.elasticsearch.common.unit.TimeValue;
import org.elasticsearch.common.xcontent.XContentType;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.search.SearchHit;
import org.elasticsearch.search.SearchHits;
import org.elasticsearch.search.builder.SearchSourceBuilder;
import org.elasticsearch.search.fetch.subphase.highlight.HighlightBuilder;
import org.elasticsearch.search.fetch.subphase.highlight.HighlightField;
import org.example.elasticsearch.constant.EsIndexName;
import org.example.elasticsearch.entity.Goods;
import org.example.elasticsearch.service.GoodsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicBoolean;

@Service
public class GoodsServiceImpl implements GoodsService {

    @Autowired
    private RestHighLevelClient client;

    @Override
    public boolean batchInsertGoods(List<Goods> goodsList) throws IOException {
        BulkRequest request = new BulkRequest();
        goodsList.stream().forEach(goods -> {
            IndexRequest indexRequest = new IndexRequest(EsIndexName.GOODS.toString(), "_doc");
            ObjectMapper mapper = new ObjectMapper();
            String json = "";
            try {
                json = mapper.writeValueAsString(goods);
            } catch (JsonProcessingException e) {
                e.printStackTrace();
                return;
            }
            indexRequest.source(json, XContentType.JSON);
            request.add(indexRequest);
        });

        AtomicBoolean isSuccess = new AtomicBoolean(true);
        BulkResponse responses = client.bulk(request, RequestOptions.DEFAULT);
        responses.forEach(item -> {
            System.out.println(item.status().getStatus()+" "+ item.isFailed() +" " + item.getResponse());
            if(item.isFailed()){
                isSuccess.set(false);
            }
        });
        return isSuccess.get();
    }

    @Override
    public List<Map<String, Object>> queryGoodsByName(String name, int pageNo, int pageSize) throws IOException {
        String FIELD = "name";
        SearchRequest searchRequest = new SearchRequest(EsIndexName.GOODS.toString());
        SearchSourceBuilder sourceBuilder = new SearchSourceBuilder();
        sourceBuilder.query(QueryBuilders.matchQuery(FIELD, name));
        sourceBuilder.from(pageNo);
        sourceBuilder.size(pageSize);
        sourceBuilder.timeout(new TimeValue(60, TimeUnit.SECONDS));

        //高亮
        HighlightBuilder highlightBuilder = new HighlightBuilder();
        HighlightBuilder.Field highlightTitle =
                new HighlightBuilder.Field(FIELD);
//        highlightTitle.highlighterType("unified");
        highlightBuilder.field(highlightTitle).preTags("<span style='color:red'>").postTags("</span>");
        sourceBuilder.highlighter(highlightBuilder);

        searchRequest.source(sourceBuilder);

        SearchResponse searchResponse = client.search(searchRequest, RequestOptions.DEFAULT);
        SearchHits hits = searchResponse.getHits();
        long totalHits = hits.getTotalHits();
        float maxScore = hits.getMaxScore();
        System.out.println("总数及最大分 "+totalHits + " "+ maxScore);
        //用高亮部分的内容替换原source中的内容
        List<Map<String, Object>> ret = new ArrayList<>();
        for (SearchHit hit : hits.getHits()) {
            Map<String, Object> sourceAsMap = hit.getSourceAsMap();

            sourceAsMap.put("desc", sourceAsMap.get(FIELD));//先将name字段的内容放入desc中，方便放入页面元素的title属性
            Map<String, HighlightField> highlightFields = hit.getHighlightFields();
            HighlightField highlight = highlightFields.get(FIELD);
            Text[] fragments = highlight.fragments();
            String fragmentString = fragments[0].string();
            // do something with the SearchHit
            sourceAsMap.put(FIELD, fragmentString);
            ret.add(sourceAsMap);
        }

        return ret;
    }
}
