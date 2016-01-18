package com.gyz.androiddevelope.net;

import android.content.Context;
import android.content.res.XmlResourceParser;

import com.gyz.androiddevelope.R;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * @author: guoyazhou
 * @date: 2016-01-12 14:41
 */
public class UrlConfigManager {
    private static final String TAG = "UrlConfigManager";

    private static List<UrlData> list = null;

    public static UrlData findUrlData(Context context, String key) {

        if (list == null || list.isEmpty())
            fetchUrlFromXml(context);

        for (UrlData urlData : list) {
            if (key.equals(urlData.getKey()))
                return urlData;
        }
        return null;
    }

    private static void fetchUrlFromXml(Context context) {
        list = new ArrayList<>();
        XmlResourceParser parser = context.getResources().getXml(R.xml.url);
        int eventType;
        try {
            eventType = parser.getEventType();

            while (eventType != XmlPullParser.END_DOCUMENT) {
                switch (eventType) {
                    case XmlPullParser.START_DOCUMENT:

                        break;
                    case XmlPullParser.START_TAG:

                        if ("Node".equals(parser.getName())) {
                            String keyStr = parser.getAttributeValue(null, "Key");

                            UrlData urlData = new UrlData();
                            urlData.setKey(keyStr);
                            urlData.setExpires(Long.parseLong(parser.getAttributeValue(null, "Expires")));
                            urlData.setNetType(parser.getAttributeValue(null, "NetType"));
                            urlData.setUrl(parser.getAttributeValue(null, "Url"));

                            list.add(urlData);
                        }

                    case XmlPullParser.END_TAG:
                        break;
                }
                eventType = parser.next();
            }
        } catch (XmlPullParserException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            parser.close();
        }
    }
}
