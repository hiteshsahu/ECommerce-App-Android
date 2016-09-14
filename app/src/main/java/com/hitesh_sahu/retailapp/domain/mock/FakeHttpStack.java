//package com.hitesh_sahu.retailapp.domain.mock;
//
//import android.content.Context;
//
//import com.android.volley.AuthFailureError;
//import com.android.volley.Request;
//import com.android.volley.toolbox.HttpStack;
//import com.android.volley.toolbox.StringRequest;
//import com.google.common.base.Charsets;
//import com.google.common.collect.Lists;
//import com.google.common.io.CharStreams;
//import com.hitesh_sahu.retailapp.domain.helper.NetworkConstants;
//
//import org.apache.http.Header;
//import org.apache.http.HttpEntity;
//import org.apache.http.HttpResponse;
//import org.apache.http.HttpVersion;
//import org.apache.http.entity.StringEntity;
//import org.apache.http.message.BasicHeader;
//import org.apache.http.message.BasicHttpResponse;
//import org.apache.http.message.BasicStatusLine;
//
//import java.io.File;
//import java.io.IOException;
//import java.io.InputStream;
//import java.io.InputStreamReader;
//import java.io.UnsupportedEncodingException;
//import java.net.URL;
//import java.text.DateFormat;
//import java.text.SimpleDateFormat;
//import java.util.Date;
//import java.util.List;
//import java.util.Map;
//
//public class FakeHttpStack implements HttpStack {
//    private static final int SIMULATED_DELAY_MS = 500;
//    private final Context context;
//
//    FakeHttpStack(Context context) {
//        this.context = context;
//    }
//
//    @Override
//    public HttpResponse performRequest(Request<?> request,
//                                       Map<String, String> stringStringMap) throws IOException,
//            AuthFailureError {
//        try {
//            Thread.sleep(SIMULATED_DELAY_MS);
//        } catch (InterruptedException e) {
//        }
//        HttpResponse response = new BasicHttpResponse(new BasicStatusLine(
//                HttpVersion.HTTP_1_1, 200, "OK"));
//        List<Header> headers = defaultHeaders();
//        response.setHeaders(headers.toArray(new Header[0]));
//        // response.setLocale(Locale.JAPAN);
//        response.setEntity(createEntity(request));
//        return response;
//    }
//
//    private List<Header> defaultHeaders() {
//        DateFormat dateFormat = new SimpleDateFormat(
//                "EEE, dd mmm yyyy HH:mm:ss zzz");
//        return Lists.<Header>newArrayList(
//                new BasicHeader("Date", dateFormat.format(new Date())),
//                new BasicHeader("Server",
//                /*
//				 * To use the appropriate server information that came back from
//				 * Sakura server
//				 */
//                        "Apache/1.3.42 (Unix) mod_ssl/2.8.31 OpenSSL/0.9.8e"));
//    }
//
//    private HttpEntity createEntity(Request request)
//            throws UnsupportedEncodingException {
//
//        if (request.getOriginUrl().equalsIgnoreCase(
//                NetworkConstants.URL_GET_ALL_CATEGORY)) {
//            return new StringEntity(NetworkConstants.GET_CATEGORY_RESPONSE);
//        }
//
//        File file = getFileFromPath(this, "res/testfile.txt");
//        String resourceName = file.toString();
//        System.out.println("resourceName found " + resourceName);
//        if (!file.exists()) {
//            System.out.println("No fake file named " + resourceName
//                    + " default fake response should be used.");
//        } else {
//            System.out.println("resourceName found " + file.getName());
//            try {
//                InputStream stream = context.openFileInput(resourceName);
//                String string = CharStreams.toString(new InputStreamReader(
//                        stream, Charsets.UTF_8));
//                if ("randomInt".equals(string)) {
//                    string = Integer
//                            .toString((int) (Math.random() * Integer.MAX_VALUE));
//                }
//
//                // Return Fake Response
//                return new StringEntity(string);
//            } catch (IOException e) {
//                System.out.println("error reading " + resourceName + e);
//            }
//        }
//        // Since there is no appropriate resources, it returns appropriately
//        if (request instanceof StringRequest) {
//
//            // Return fake string
//            return new StringEntity("100");
//
//        } else {
//
//            // Return fake array
//            return new StringEntity(
//                    " {\"a\":1,\"b\":2,\"c\":3,\"d\":4,\"e\":5}");
//        }
//    }
//
//    private static File getFileFromPath(Object obj, String fileName) {
//        ClassLoader classLoader = obj.getClass().getClassLoader();
//        URL resource = classLoader.getResource(fileName);
//        return new File(resource.getPath());
//    }
//}