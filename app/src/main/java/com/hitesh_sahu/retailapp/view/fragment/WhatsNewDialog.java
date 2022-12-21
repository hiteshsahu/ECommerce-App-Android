/*
 * Copyright (c) 2017. http://hiteshsahu.com- All Rights Reserved
 * Unauthorized copying of this file, via any medium is strictly prohibited
 * If you use or distribute this project then you MUST ADD A COPY OF LICENCE
 * along with the project.
 *  Written by Hitesh Sahu <hiteshkrsahu@Gmail.com>, 2017.
 */

/*
 * (c) 2012 Martin van Zuilekom (http://martin.cubeactive.com)
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 *
 */
package com.hitesh_sahu.retailapp.view.fragment;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.DialogInterface.OnCancelListener;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager.NameNotFoundException;
import android.content.res.Resources;
import android.content.res.XmlResourceParser;
import android.text.format.DateFormat;
import android.util.Log;
import android.webkit.WebView;

import com.hitesh_sahu.retailapp.R;
import com.hitesh_sahu.retailapp.util.PreferenceHelper;
import com.hitesh_sahu.retailapp.util.Utils;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;

import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Class to show a dialog with the latest changes for the current app version.
 */
public class WhatsNewDialog {

    private static final String TAG = "ChangeLogDialog";
    private final Context mContext;
    private String mStyle = "h1 { margin-left: 0px; font-size: 12pt; }"
            + "li { margin-left: 0px; font-size: 9pt; }"
            + "ul { padding-left: 30px; }"
            + ".summary { font-size: 9pt; color: #606060; display: block; clear: left; }"
            + ".date { font-size: 9pt; color: #606060;  display: block; }";

    public WhatsNewDialog(final Context context) {
        mContext = context;

        show();
    }

    private void show() {

        if (!PreferenceHelper.getPreferenceHelperInstance()
                .getString(mContext, PreferenceHelper.WHATS_NEW_LAST_SHOWN, "0")
                .equalsIgnoreCase(Utils.getVersion(mContext))) {

            // This version is new, show only the changes from this version (if available)

            show(Utils.getVersion(mContext));

            PreferenceHelper.getPreferenceHelperInstance().setString(
                    mContext, PreferenceHelper.WHATS_NEW_LAST_SHOWN,
                    Utils.getVersion(mContext));
        }
    }

    // Get the current app version
    private String getAppVersion() {
        String versionName = "";
        try {
            final PackageInfo packageInfo = mContext.getPackageManager()
                    .getPackageInfo(mContext.getPackageName(), 0);
            versionName = packageInfo.versionName;
        } catch (NameNotFoundException e) {
            Log.e(TAG, e.getMessage(), e);
        }
        return versionName;
    }

    // Parse a date string from the xml and format it using the local date
    // format
    @SuppressLint("SimpleDateFormat")
    private String parseDate(final String dateString) {
        final SimpleDateFormat dateFormat = new SimpleDateFormat("MM/dd/yyyy");
        try {
            final Date parsedDate = dateFormat.parse(dateString);
            return DateFormat.getDateFormat(mContext).format(parsedDate);
        } catch (ParseException ignored) {
            // If there is a problem parsing the date just return the original
            // string
            return dateString;
        }
    }

    // Parse a the release tag and appends it to the changelog builder
    private void parseReleaseTag(final StringBuilder changelogBuilder,
                                 final XmlPullParser resourceParser) throws XmlPullParserException,
            IOException {
        changelogBuilder.append("<h1>Release: ")
                .append(resourceParser.getAttributeValue(null, "version"))
                .append("</h1>");

        // Add date if available
        if (resourceParser.getAttributeValue(null, "date") != null) {
            changelogBuilder
                    .append("<span class='date'>")
                    .append(parseDate(resourceParser.getAttributeValue(null,
                            "date"))).append("</span>");
        }

        // Add summary if available
        if (resourceParser.getAttributeValue(null, "summary") != null) {
            changelogBuilder.append("<span class='summary'>")
                    .append(resourceParser.getAttributeValue(null, "summary"))
                    .append("</span>");
        }

        changelogBuilder.append("<ul>");

        // Parse child nodes
        int eventType = resourceParser.getEventType();
        while ((eventType != XmlPullParser.END_TAG)
                || (resourceParser.getName().equals("change"))) {
            if ((eventType == XmlPullParser.START_TAG)
                    && (resourceParser.getName().equals("change"))) {
                eventType = resourceParser.next();
                changelogBuilder.append("<li>" + resourceParser.getText()
                        + "</li>");
            }
            eventType = resourceParser.next();
        }
        changelogBuilder.append("</ul>");
    }

    // CSS style for the html
    private String getStyle() {
        return String.format("<style type=\"text/css\">%s</style>", mStyle);
    }

    public void setStyle(final String style) {
        mStyle = style;
    }

    // Get the changelog in html code, this will be shown in the dialog's
    // webview
    private String getHTMLChangelog(final int resourceId,
                                    final Resources resources, final String version) {
        boolean releaseFound = false;
        final StringBuilder changelogBuilder = new StringBuilder();
        changelogBuilder.append("<html><head>").append(getStyle())
                .append("</head><body>");
        final XmlResourceParser xml = resources.getXml(resourceId);
        try {
            int eventType = xml.getEventType();
            while (eventType != XmlPullParser.END_DOCUMENT) {
                if ((eventType == XmlPullParser.START_TAG)
                        && (xml.getName().equals("release"))) {
                    // Check if the version matches the release tag.
                    // When version is 0 every release tag is parsed.
                    final String versioncode = xml.getAttributeValue(null,
                            "versioncode");
                    if ((null != version) || (versioncode == version)) {
                        parseReleaseTag(changelogBuilder, xml);
                        releaseFound = true; // At lease one release tag has
                        // been parsed.
                    }
                }
                eventType = xml.next();
            }
        } catch (XmlPullParserException e) {
            Log.e(TAG, e.getMessage(), e);
            return "";
        } catch (IOException e) {
            Log.e(TAG, e.getMessage(), e);
            return "";
        } finally {
            xml.close();
        }
        changelogBuilder.append("</body></html>");

        // Check if there was a release tag parsed, if not return an empty
        // string.
        if (releaseFound) {
            return changelogBuilder.toString();
        } else {
            return "";
        }
    }

    // Returns change log in HTML format
    public String getHTML() {
        // TODO: Remove duplicate code with the method show()
        // Get resources
        final String packageName = mContext.getPackageName();
        final Resources resources;
        try {
            resources = mContext.getPackageManager()
                    .getResourcesForApplication(packageName);
        } catch (NameNotFoundException ignored) {
            return "";
        }

        // Create HTML change log
        return getHTMLChangelog(R.xml.changelog, resources, "0");
    }

    protected void show(final String string) {
        // Get resources
        final String packageName = mContext.getPackageName();
        final Resources resources;
        try {
            resources = mContext.getPackageManager()
                    .getResourcesForApplication(packageName);
        } catch (NameNotFoundException ignored) {
            return;
        }

        // Get dialog title
        String title = resources.getString(R.string.title_changelog);
        title = String.format("%s v%s", title, getAppVersion());

        // Create html change log
        final String htmlChangelog = getHTMLChangelog(R.xml.changelog,
                resources, string);

        // Get button strings
        final String closeString = resources
                .getString(R.string.changelog_close);

        // Check for empty change log
        if (htmlChangelog.length() == 0) {
            // It seems like there is nothing to show, just bail out.
            return;
        }

        // Create web view and load html
        final WebView webView = new WebView(mContext);

        webView.loadDataWithBaseURL(null, htmlChangelog, "text/html", "utf-8",
                null);

        final AlertDialog.Builder builder = new AlertDialog.Builder(mContext)
                .setTitle(title).setView(webView)

                .setPositiveButton(closeString, new Dialog.OnClickListener() {
                    public void onClick(final DialogInterface dialogInterface,
                                        final int i) {
                        dialogInterface.dismiss();
                    }
                }).setOnCancelListener(new OnCancelListener() {

                    @Override
                    public void onCancel(DialogInterface dialog) {
                        dialog.dismiss();
                    }
                });


        AlertDialog dialog = builder.create();

        dialog.setOnDismissListener(new DialogInterface.OnDismissListener() {
            @Override
            public void onDismiss(final DialogInterface dialog) {
            }
        });
        dialog.show();
    }
}
