package com.gyz.androiddevelope.net;

import android.content.Context;
import android.content.res.XmlResourceParser;
import com.gyz.androiddevelope.R;
import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;
import java.io.IOException;

/**
 * @author: guoyazhou
 * @date: 2016-01-12 14:41
 */
public class UrlConfigManager {
    private static final String TAG = "UrlConfigManager";

    public static UrlData getUrl(String key, Context context) {

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
                            if (keyStr.trim().equals(key)) {

                                UrlData urlData = new UrlData();
                                urlData.setKey(key);
                                urlData.setExpires(Long.parseLong(parser.getAttributeValue(null, "Expires")));
                                urlData.setNetType(parser.getAttributeValue(null, "NetType"));
                                urlData.setUrl(parser.getAttributeValue(null, "Url"));
                                return urlData;
                            }
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
        return null;
    }
}
