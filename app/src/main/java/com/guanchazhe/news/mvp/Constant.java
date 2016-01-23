package com.guanchazhe.news.mvp;

import com.guanchazhe.news.R;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by ranzh on 1/14/2016.
 */
public class Constant {
    private static final int DEFAULTCHANNELID = 49646;

    public static final int ANIMATOR_VIEW_LOADING = R.id.view_loading;
    public static final int ANIMATOR_VIEW_ERROR = R.id.asv_list_content_errorview;

    public static final String SHAREDPREFERENCE = "PREFERENCE";
    public static final String FAVORITECHANNELS = "favoriteChannels";
    public static final String NEWSURL = "newsURL";
    public static final String AUTHORNAME = "authorName";

    public static final String NEWSDETAILURLPREFIX = "http://mobileservice.guancha.cn/Appdetail/get/?devices=android&id=";
    public static final String NEWSDETAILWEBURLPREFIX = "http://mobileservice.guancha.cn/Appdetail/get/?devices=android&webid=";



    public enum RequestType {
        ASK,
        ASKMORE
    }

    public enum NewsType {
        NEWS,
        COMMENTARY,
        HYBRID
    }

    private static Map<String, Integer> ChannelMap;
    private static List<String> DefaultChannels;

    static {
        ChannelMap = new HashMap<>();

        ChannelMap.put("要闻", 49646);
        ChannelMap.put("缤纷", 49646);
        ChannelMap.put("时评", 49646);

        ChannelMap.put("政治", 49642);
        ChannelMap.put("军事", 61935);
        ChannelMap.put("财经", 60261);
        ChannelMap.put("文化", 60432);
        ChannelMap.put("周边", 49641);
        ChannelMap.put("历史", 51417);
        ChannelMap.put("区域", 49875);
        ChannelMap.put("社会", 50459);
        ChannelMap.put("北美", 49630);
        ChannelMap.put("欧洲", 60260);
        ChannelMap.put("科技", 60303);
        ChannelMap.put("产业", 60363);
        ChannelMap.put("传媒", 49643);
        ChannelMap.put("战略", 60430);
        ChannelMap.put("工程", 60298);
        ChannelMap.put("体育", 60433);
        ChannelMap.put("生活", 60271);
        ChannelMap.put("教育", 49639);
        ChannelMap.put("亚非拉", 60422);
    }

    static {
        DefaultChannels = new ArrayList<>();

        DefaultChannels.add("社会");
        DefaultChannels.add("财经");
        DefaultChannels.add("政治");
    }

    public static int getChannelID(String key) {

        if (ChannelMap.containsKey(key)) {
            return ChannelMap.get(key);
        }
        return DEFAULTCHANNELID;
    }

    public static List<String> getDefaultChannels() {
        return DefaultChannels;
    }

}
