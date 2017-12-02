package com.getbase.services;

import com.getbase.http.HttpClient;
import com.getbase.models.Source;
import com.getbase.serializer.JsonDeserializer;
import com.getbase.serializer.JsonSerializer;
import com.getbase.serializer.Views;
import com.getbase.utils.Joiner;

import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import static com.getbase.utils.Lists.asList;
import static com.getbase.utils.Precondition.checkArgument;
import static com.getbase.utils.Precondition.checkNotNull;


public class LeadSourcesService extends BaseService {
  public LeadSourcesService(HttpClient httpClient) {
    super(httpClient);
  }

  public List<Source> list(Map<String, Object> params) {
    String url = "/v2/lead_sources";
    return JsonDeserializer.deserializeList(this.httpClient.get(url, params).getBody(), Source.class);
  }

  public List<Source> list(SearchCriteria criteria) {
    return list(criteria.asMap());
  }


  public Source create(Source source) {
    checkNotNull(source, "source parameter must not be null");

    String url = "/v2/lead_sources";
    String serialized = JsonSerializer.serialize(source, Views.ReadWrite.class);
    return JsonDeserializer.deserialize(this.httpClient.post(url, serialized).getBody(), Source.class);
  }

  public Source create(Map<String, Object> attributes) {
    checkNotNull(attributes, "attributes parameter must not be null");
    
    String url = "/v2/lead_sources";
    String serialized = JsonSerializer.serialize(attributes);
    return JsonDeserializer.deserialize(this.httpClient.post(url, serialized).getBody(), Source.class);
  }


  public Source get(long id) {
    checkArgument(id > 0, "id must be a valid id");

    String url = String.format(Locale.US, "/v2/lead_sources/%d", id);
    return JsonDeserializer.deserialize(this.httpClient.get(url, null).getBody(), Source.class);
  }


  public Source update(Source source) {
    checkNotNull(source, "source parameter must not be null");
    checkNotNull(source.getId(), "source must have id attribute set");
    checkArgument(source.getId() > 0, "source id must be a valid id");

    String url = String.format(Locale.US, "/v2/lead_sources/%d", source.getId());
    String serialized = JsonSerializer.serialize(source, Views.ReadWrite.class);
    return JsonDeserializer.deserialize(this.httpClient.put(url, serialized).getBody(), Source.class);
  }

  public Source update(long id, Map<String, Object> attributes) {
    checkArgument(id > 0, "id must be a valid id");
    checkNotNull(attributes, "attributes parameter must not be null");

    String url = String.format(Locale.US, "/v2/lead_sources/%d", id);
    String serialized = JsonSerializer.serialize(attributes);
    return JsonDeserializer.deserialize(this.httpClient.put(url, serialized).getBody(), Source.class);
  }


  public boolean delete(long id) {
    checkArgument(id > 0, "id must be a valid id");
    
    String url = String.format(Locale.US, "/v2/lead_sources/%d", id);
    return this.httpClient.delete(url, null).getHttpStatus() == 204;
  }



  public static class SearchCriteria {
    private Map<String, Object> queryParams;

    public SearchCriteria() {
      this.queryParams = new HashMap<String, Object>();
    }

    public SearchCriteria page(long page) {
      queryParams.put("page", page);
      return this;
    }

    public SearchCriteria perPage(long perPage) {
      queryParams.put("per_page", perPage);
      return this;
    }

    public SearchCriteria sortBy(String criteria, String order) {
      queryParams.put("sort_by", criteria + ":" + order);
      return this;
    }

    public SearchCriteria sortBy(String criteria) {
      return sortBy(criteria, "asc");
    }

    public SearchCriteria ids(List<Long> ids) {
      queryParams.put("ids", Joiner.join(",", ids));
      return this;
    }

    public SearchCriteria ids(Long... ids) {
      return ids(asList(ids));
    }

    public SearchCriteria name(String name) {
      queryParams.put("name", name);
      return this;
    }

    public Map<String, Object> asMap() {
      return Collections.unmodifiableMap(queryParams);
    }
  }

}

