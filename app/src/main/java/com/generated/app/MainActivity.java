package com.generated.app;

import android.app.Activity;
import android.app.AlertDialog;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.Gravity;
import android.view.View;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class MainActivity extends Activity {

    private final List<NewsArticle> mAllArticles = new ArrayList<>();
    private final List<ZimPackage> mPackages = new ArrayList<>();

    // UI Root views
    private LinearLayout mMainContentContainer;
    private View mTabPackagesView;
    private View mTabBuilderView;
    private View mTabArticlesView;
    private View mReaderView;

    // Header buttons & indicator
    private Button mBtnTabPackages;
    private Button mBtnTabBuilder;
    private Button mBtnTabArticles;
    private TextView mTvStatusIndicator;

    // Package list container
    private LinearLayout mPackagesListLayout;
    private LinearLayout mArticlesListLayout;

    // Builder controls
    private CheckBox mCbGeneralNews;
    private CheckBox mCbTorah;
    private CheckBox mCbMusic;
    private CheckBox mCbEconomy;
    private CheckBox mCbCommunity;
    private CheckBox mCbIncludeVideos;
    private RadioGroup mRgDateRange;
    private Button mBtnStartPackaging;
    private LinearLayout mBuildingProgressCard;
    private ProgressBar mBuildProgressBar;
    private TextView mTvProgressStatus;
    private TextView mTvProgressPercent;

    // Reader controls
    private WebView mZimWebView;
    private TextView mTvReaderTitle;
    private TextView mTvReaderSubtitle;
    private Button mBtnBackFromReader;

    private boolean mIsSimulatedOffline = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Load repository items
        mAllArticles.addAll(NewsRepository.getAllInitialArticles());

        // Create standard pre-packaged daily edition
        createPrepackagedDailyZim();

        // Build entire dynamic UI with zero third-party dependencies
        initUi();

        // Show packages tab initially
        showTab(0);
    }

    private void createPrepackagedDailyZim() {
        String dailyHtml = NewsRepository.buildZimHtmlArchive(
                "מהדורת היום המרכזית (ZIM) - כלל הערוצים",
                mAllArticles,
                false
        );

        List<String> sources = Arrays.asList(
                "הגיזרה", "זירת החדשות", "הקו החרדי", "שטייגען",
                "מורשת מרן", "מיוזיק אונליין", "מסודרים", "My Ali"
        );

        ZimPackage defaultPack = new ZimPackage(
                "pack_today_standard",
                "news_archive_today_all.zim",
                "מהדורת היום המרכזית - כלל הערוצים",
                "הופק הבוקר 08:00 (מוכן מראש)",
                "4.8 מ\"ב (טקסט ותמונות)",
                mAllArticles.size(),
                false,
                sources,
                dailyHtml
        );
        mPackages.add(defaultPack);

        // Add a specialized Torah package
        List<NewsArticle> torahArticles = new ArrayList<>();
        for (NewsArticle a : mAllArticles) {
            if ("תורה ומורשת".equals(a.getCategory())) {
                torahArticles.add(a);
            }
        }
        String torahHtml = NewsRepository.buildZimHtmlArchive(
                "מהדורת תורה, מורשת וישיבות (ZIM)",
                torahArticles,
                false
        );
        ZimPackage torahPack = new ZimPackage(
                "pack_torah_only",
                "torah_and_heritage.zim",
                "מהדורת תורה, מורשת ושיעורים",
                "עודכן אתמול 22:00",
                "2.1 מ\"ב (טקסט מלא)",
                torahArticles.size(),
                false,
                Arrays.asList("שטייגען", "מורשת מרן אונליין", "מעיינות ונחלים"),
                torahHtml
        );
        mPackages.add(torahPack);
    }

    private void initUi() {
        LinearLayout root = new LinearLayout(this);
        root.setOrientation(LinearLayout.VERTICAL);
        root.setLayoutParams(new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.MATCH_PARENT
        ));
        root.setBackgroundColor(Color.parseColor("#F1F5F9"));

        // Top App Bar
        LinearLayout header = createHeader();
        root.addView(header);

        // Tab Navigation Bar
        LinearLayout tabNav = createTabNavigation();
        root.addView(tabNav);

        // Main Dynamic Frame
        mMainContentContainer = new LinearLayout(this);
        mMainContentContainer.setOrientation(LinearLayout.VERTICAL);
        mMainContentContainer.setLayoutParams(new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT,
                0,
                1f
        ));

        // Create the views for each tab & reader
        mTabPackagesView = createPackagesTabView();
        mTabBuilderView = createBuilderTabView();
        mTabArticlesView = createArticlesTabView();
        mReaderView = createReaderView();

        mMainContentContainer.addView(mTabPackagesView);
        mMainContentContainer.addView(mTabBuilderView);
        mMainContentContainer.addView(mTabArticlesView);
        mMainContentContainer.addView(mReaderView);

        root.addView(mMainContentContainer);

        setContentView(root);
    }

    private LinearLayout createHeader() {
        LinearLayout header = new LinearLayout(this);
        header.setOrientation(LinearLayout.VERTICAL);
        header.setBackgroundColor(Color.parseColor("#0D3B66"));
        header.setPadding(dp(16), dp(16), dp(16), dp(14));

        LinearLayout topRow = new LinearLayout(this);
        topRow.setOrientation(LinearLayout.HORIZONTAL);
        topRow.setGravity(Gravity.CENTER_VERTICAL);

        TextView title = new TextView(this);
        title.setText(R.string.app_name);
        title.setTextColor(Color.WHITE);
        title.setTextSize(20);
        title.setTypeface(null, android.graphics.Typeface.BOLD);
        LinearLayout.LayoutParams tLp = new LinearLayout.LayoutParams(0, LinearLayout.LayoutParams.WRAP_CONTENT, 1f);
        topRow.addView(title, tLp);

        // Toggle Offline Mode Button
        final Button btnOfflineToggle = new Button(this);
        btnOfflineToggle.setText("רשת: מקוון");
        btnOfflineToggle.setTextSize(11);
        btnOfflineToggle.setTextColor(Color.parseColor("#0D3B66"));
        btnOfflineToggle.setBackgroundResource(R.drawable.tab_selected);
        btnOfflineToggle.setPadding(dp(12), dp(4), dp(12), dp(4));
        btnOfflineToggle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mIsSimulatedOffline = !mIsSimulatedOffline;
                if (mIsSimulatedOffline) {
                    btnOfflineToggle.setText("מצב: אופליין מלא ✈");
                    btnOfflineToggle.setTextColor(Color.WHITE);
                    btnOfflineToggle.setBackgroundColor(Color.parseColor("#F95738"));
                    mTvStatusIndicator.setText("פעיל במצב אופליין מלא • קריאה בלעדית מארכיוני ZIM שמורים");
                    Toast.makeText(MainActivity.this, "האפליקציה עברה למצב אופליין (ללא חיבור אינטרנט)", Toast.LENGTH_SHORT).show();
                } else {
                    btnOfflineToggle.setText("רשת: מקוון");
                    btnOfflineToggle.setTextColor(Color.parseColor("#0D3B66"));
                    btnOfflineToggle.setBackgroundResource(R.drawable.tab_selected);
                    mTvStatusIndicator.setText("מחובר לרשת • ניתן להוריד מהדורות ZIM עדכניות ולקרוא חופשי");
                    Toast.makeText(MainActivity.this, "מצב מקוון הופעל", Toast.LENGTH_SHORT).show();
                }
            }
        });
        topRow.addView(btnOfflineToggle);

        header.addView(topRow);

        mTvStatusIndicator = new TextView(this);
        mTvStatusIndicator.setText("מחובר לרשת • ניתן להוריד מהדורות ZIM עדכניות ולקרוא חופשי");
        mTvStatusIndicator.setTextColor(Color.parseColor("#FAF0CA"));
        mTvStatusIndicator.setTextSize(12);
        mTvStatusIndicator.setPadding(0, dp(4), 0, 0);
        header.addView(mTvStatusIndicator);

        return header;
    }

    private LinearLayout createTabNavigation() {
        LinearLayout tabs = new LinearLayout(this);
        tabs.setOrientation(LinearLayout.HORIZONTAL);
        tabs.setBackgroundColor(Color.parseColor("#0D3B66"));
        tabs.setPadding(dp(12), 0, dp(12), dp(10));

        mBtnTabPackages = createTabButton("מהדורות שמורות (ZIM)", 0);
        mBtnTabBuilder = createTabButton("אריזה מותאמת", 1);
        mBtnTabArticles = createTabButton("מאגר כתבות", 2);

        tabs.addView(mBtnTabPackages);
        tabs.addView(mBtnTabBuilder);
        tabs.addView(mBtnTabArticles);

        return tabs;
    }

    private Button createTabButton(String text, final int tabIndex) {
        Button b = new Button(this);
        b.setText(text);
        b.setTextSize(12);
        b.setPadding(dp(8), dp(8), dp(8), dp(8));
        LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(0, dp(42), 1f);
        lp.setMargins(dp(3), 0, dp(3), 0);
        b.setLayoutParams(lp);

        b.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showTab(tabIndex);
            }
        });
        return b;
    }

    private void showTab(int tabIndex) {
        mTabPackagesView.setVisibility(tabIndex == 0 ? View.VISIBLE : View.GONE);
        mTabBuilderView.setVisibility(tabIndex == 1 ? View.VISIBLE : View.GONE);
        mTabArticlesView.setVisibility(tabIndex == 2 ? View.VISIBLE : View.GONE);
        mReaderView.setVisibility(View.GONE);

        updateTabStyle(mBtnTabPackages, tabIndex == 0);
        updateTabStyle(mBtnTabBuilder, tabIndex == 1);
        updateTabStyle(mBtnTabArticles, tabIndex == 2);

        if (tabIndex == 0) {
            refreshPackagesList();
        } else if (tabIndex == 2) {
            refreshArticlesList("");
        }
    }

    private void updateTabStyle(Button b, boolean selected) {
        if (selected) {
            b.setBackgroundResource(R.drawable.tab_selected);
            b.setTextColor(Color.parseColor("#0D3B66"));
            b.setTypeface(null, android.graphics.Typeface.BOLD);
        } else {
            b.setBackgroundResource(R.drawable.tab_unselected);
            b.setTextColor(Color.parseColor("#FAF0CA"));
            b.setTypeface(null, android.graphics.Typeface.NORMAL);
        }
    }

    // -------------------------------------------------------------
    // TAB 1: PACKAGES & QUICK DOWNLOAD
    // -------------------------------------------------------------
    private View createPackagesTabView() {
        ScrollView scroll = new ScrollView(this);
        scroll.setLayoutParams(new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.MATCH_PARENT
        ));

        LinearLayout content = new LinearLayout(this);
        content.setOrientation(LinearLayout.VERTICAL);
        content.setPadding(dp(16), dp(16), dp(16), dp(24));

        // Quick Download Hero Banner
        LinearLayout heroCard = new LinearLayout(this);
        heroCard.setOrientation(LinearLayout.VERTICAL);
        heroCard.setBackgroundResource(R.drawable.card_background);
        heroCard.setPadding(dp(18), dp(16), dp(18), dp(16));

        TextView heroTitle = new TextView(this);
        heroTitle.setText("הורדה מיידית בלחיצת כפתור ⚡");
        heroTitle.setTextSize(16);
        heroTitle.setTypeface(null, android.graphics.Typeface.BOLD);
        heroTitle.setTextColor(Color.parseColor("#0D3B66"));
        heroCard.addView(heroTitle);

        TextView heroDesc = new TextView(this);
        heroDesc.setText("קבל קובץ ZIM של מהדורת היום הכולל את כל ערוצי החדשות (הגיזרה, זירת החדשות, הקו החרדי, מורשת מרן, שטייגען, כלכלה ומוזיקה) מוכן לפתיחה אופליין מיד!");
        heroDesc.setTextSize(13);
        heroDesc.setTextColor(Color.parseColor("#475569"));
        heroDesc.setPadding(0, dp(6), 0, dp(12));
        heroCard.addView(heroDesc);

        Button btnDownloadNow = new Button(this);
        btnDownloadNow.setText("📥 פתח מהדורת יום מוכנה (ZIM) עכשיו");
        btnDownloadNow.setBackgroundResource(R.drawable.btn_primary);
        btnDownloadNow.setTextColor(Color.WHITE);
        btnDownloadNow.setTextSize(14);
        btnDownloadNow.setTypeface(null, android.graphics.Typeface.BOLD);
        btnDownloadNow.setPadding(dp(16), dp(12), dp(16), dp(12));
        btnDownloadNow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!mPackages.isEmpty()) {
                    openZimReader(mPackages.get(0));
                }
            }
        });
        heroCard.addView(btnDownloadNow);

        content.addView(heroCard);

        // Section Title: Available offline archives
        TextView tvSection = new TextView(this);
        tvSection.setText("מהדורות ZIM שנשמרו במכשיר לקריאה אופליין:");
        tvSection.setTextSize(15);
        tvSection.setTypeface(null, android.graphics.Typeface.BOLD);
        tvSection.setTextColor(Color.parseColor("#1E293B"));
        tvSection.setPadding(0, dp(20), 0, dp(10));
        content.addView(tvSection);

        mPackagesListLayout = new LinearLayout(this);
        mPackagesListLayout.setOrientation(LinearLayout.VERTICAL);
        content.addView(mPackagesListLayout);

        scroll.addView(content);
        return scroll;
    }

    private void refreshPackagesList() {
        mPackagesListLayout.removeAllViews();

        for (final ZimPackage pack : mPackages) {
            LinearLayout card = new LinearLayout(this);
            card.setOrientation(LinearLayout.VERTICAL);
            card.setBackgroundResource(R.drawable.card_background);
            card.setPadding(dp(16), dp(14), dp(16), dp(14));
            LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(
                    LinearLayout.LayoutParams.MATCH_PARENT,
                    LinearLayout.LayoutParams.WRAP_CONTENT
            );
            lp.setMargins(0, 0, 0, dp(14));
            card.setLayoutParams(lp);

            LinearLayout topRow = new LinearLayout(this);
            topRow.setOrientation(LinearLayout.HORIZONTAL);
            topRow.setGravity(Gravity.CENTER_VERTICAL);

            TextView tvName = new TextView(this);
            tvName.setText(pack.getDisplayName());
            tvName.setTextSize(16);
            tvName.setTypeface(null, android.graphics.Typeface.BOLD);
            tvName.setTextColor(Color.parseColor("#0D3B66"));
            LinearLayout.LayoutParams nameLp = new LinearLayout.LayoutParams(0, LinearLayout.LayoutParams.WRAP_CONTENT, 1f);
            topRow.addView(tvName, nameLp);

            TextView tvBadge = new TextView(this);
            tvBadge.setText(".ZIM ארכיון");
            tvBadge.setBackgroundResource(R.drawable.badge_pill);
            tvBadge.setTextColor(Color.parseColor("#0D3B66"));
            tvBadge.setTextSize(11);
            tvBadge.setTypeface(null, android.graphics.Typeface.BOLD);
            tvBadge.setPadding(dp(8), dp(3), dp(8), dp(3));
            topRow.addView(tvBadge);

            card.addView(topRow);

            TextView tvMeta = new TextView(this);
            tvMeta.setText("קובץ: " + pack.getFileName() + " • גודל: " + pack.getFileSize() + "\n" +
                    "כמות פריטים: " + pack.getArticleCount() + " כתבות • " + pack.getCreatedAt());
            tvMeta.setTextSize(12);
            tvMeta.setTextColor(Color.parseColor("#64748B"));
            tvMeta.setPadding(0, dp(6), 0, dp(10));
            card.addView(tvMeta);

            // Action Buttons Row
            LinearLayout actions = new LinearLayout(this);
            actions.setOrientation(LinearLayout.HORIZONTAL);

            Button btnOpen = new Button(this);
            btnOpen.setText("📖 פתח בקורא ZIM המובנה");
            btnOpen.setBackgroundResource(R.drawable.btn_secondary);
            btnOpen.setTextColor(Color.WHITE);
            btnOpen.setTextSize(13);
            btnOpen.setPadding(dp(14), dp(8), dp(14), dp(8));
            btnOpen.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    openZimReader(pack);
                }
            });
            LinearLayout.LayoutParams openLp = new LinearLayout.LayoutParams(0, LinearLayout.LayoutParams.WRAP_CONTENT, 1.2f);
            openLp.setMargins(0, 0, dp(8), 0);
            actions.addView(btnOpen, openLp);

            Button btnInfo = new Button(this);
            btnInfo.setText("ℹ פרטים");
            btnInfo.setBackgroundResource(R.drawable.btn_outline);
            btnInfo.setTextColor(Color.parseColor("#0D3B66"));
            btnInfo.setTextSize(13);
            btnInfo.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    showPackageInfoDialog(pack);
                }
            });
            LinearLayout.LayoutParams infoLp = new LinearLayout.LayoutParams(0, LinearLayout.LayoutParams.WRAP_CONTENT, 0.8f);
            actions.addView(btnInfo, infoLp);

            card.addView(actions);
            mPackagesListLayout.addView(card);
        }
    }

    private void showPackageInfoDialog(ZimPackage pack) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle(pack.getDisplayName());
        StringBuilder msg = new StringBuilder();
        msg.append("שם הקובץ: ").append(pack.getFileName()).append("\n");
        msg.append("גודל: ").append(pack.getFileSize()).append("\n");
        msg.append("הופק: ").append(pack.getCreatedAt()).append("\n");
        msg.append("מספר כתבות: ").append(pack.getArticleCount()).append("\n");
        msg.append("כולל סרטונים: ").append(pack.isIncludesVideo() ? "כן" : "לא (טקסט ותמונות בלבד)").append("\n\n");
        msg.append("ערוצים כלולים:\n");
        for (String src : pack.getSourceList()) {
            msg.append("• ").append(src).append("\n");
        }
        builder.setMessage(msg.toString());
        builder.setPositiveButton("סגור", null);
        builder.show();
    }

    // -------------------------------------------------------------
    // TAB 2: CUSTOM PACKAGE BUILDER
    // -------------------------------------------------------------
    private View createBuilderTabView() {
        ScrollView scroll = new ScrollView(this);
        scroll.setLayoutParams(new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.MATCH_PARENT
        ));

        LinearLayout content = new LinearLayout(this);
        content.setOrientation(LinearLayout.VERTICAL);
        content.setPadding(dp(16), dp(16), dp(16), dp(24));

        TextView title = new TextView(this);
        title.setText("יצירת קובץ ZIM מותאם אישית בלחיצת כפתור 📦");
        title.setTextSize(17);
        title.setTypeface(null, android.graphics.Typeface.BOLD);
        title.setTextColor(Color.parseColor("#0D3B66"));
        content.addView(title);

        TextView sub = new TextView(this);
        sub.setText("בחר קטגוריות, מקורות ומאפייני מדיה. המערכת תארוז עבורך את כל התוכן לקובץ ZIM קומפקטי לקריאה אופליין.");
        sub.setTextSize(13);
        sub.setTextColor(Color.parseColor("#64748B"));
        sub.setPadding(0, dp(4), 0, dp(14));
        content.addView(sub);

        // Sources Card
        LinearLayout catCard = new LinearLayout(this);
        catCard.setOrientation(LinearLayout.VERTICAL);
        catCard.setBackgroundResource(R.drawable.card_background);
        catCard.setPadding(dp(16), dp(14), dp(16), dp(14));

        TextView tvCatTitle = new TextView(this);
        tvCatTitle.setText("1. בחירת ערוצים ונושאים לקצירה:");
        tvCatTitle.setTextSize(14);
        tvCatTitle.setTypeface(null, android.graphics.Typeface.BOLD);
        tvCatTitle.setTextColor(Color.parseColor("#0D3B66"));
        tvCatTitle.setPadding(0, 0, 0, dp(8));
        catCard.addView(tvCatTitle);

        mCbGeneralNews = createStyledCheckBox("חדשות שוטפות (הגיזרה, זירת החדשות, הקו החרדי, אותנטי ניוז)", true);
        mCbTorah = createStyledCheckBox("תורה, ישיבות ומורשת (שטייגען, מורשת מרן, מעיינות ונחלים)", true);
        mCbMusic = createStyledCheckBox("מוזיקה יהודית (מיוזיק אונליין, מוזיקה אקספרס)", true);
        mCbEconomy = createStyledCheckBox("צרכנות וכלכלה (טיפים למסודרים, My Ali, בזאר המציאות)", true);
        mCbCommunity = createStyledCheckBox("קהילה, תעסוקה ומקומי (מיין שמש, עבודות איכות לחרדים)", true);

        catCard.addView(mCbGeneralNews);
        catCard.addView(mCbTorah);
        catCard.addView(mCbMusic);
        catCard.addView(mCbEconomy);
        catCard.addView(mCbCommunity);
        content.addView(catCard);

        // Date Range Card
        LinearLayout dateCard = new LinearLayout(this);
        dateCard.setOrientation(LinearLayout.VERTICAL);
        dateCard.setBackgroundResource(R.drawable.card_background);
        dateCard.setPadding(dp(16), dp(14), dp(16), dp(14));
        LinearLayout.LayoutParams dLp = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
        dLp.setMargins(0, dp(12), 0, 0);
        dateCard.setLayoutParams(dLp);

        TextView tvDateTitle = new TextView(this);
        tvDateTitle.setText("2. טווח זמנים לקצירה:");
        tvDateTitle.setTextSize(14);
        tvDateTitle.setTypeface(null, android.graphics.Typeface.BOLD);
        tvDateTitle.setTextColor(Color.parseColor("#0D3B66"));
        tvDateTitle.setPadding(0, 0, 0, dp(6));
        dateCard.addView(tvDateTitle);

        mRgDateRange = new RadioGroup(this);
        RadioButton rb1 = new RadioButton(this);
        rb1.setText("24 שעות אחרונות (מהדורת יום מומלצת)");
        rb1.setId(View.generateViewId());
        rb1.setChecked(true);

        RadioButton rb2 = new RadioButton(this);
        rb2.setText("3 ימים אחרונים");
        rb2.setId(View.generateViewId());

        RadioButton rb3 = new RadioButton(this);
        rb3.setText("שבוע אחרון (חבילה מורחבת)");
        rb3.setId(View.generateViewId());

        mRgDateRange.addView(rb1);
        mRgDateRange.addView(rb2);
        mRgDateRange.addView(rb3);
        dateCard.addView(mRgDateRange);

        content.addView(dateCard);

        // Media Options Card
        LinearLayout mediaCard = new LinearLayout(this);
        mediaCard.setOrientation(LinearLayout.VERTICAL);
        mediaCard.setBackgroundResource(R.drawable.card_background);
        mediaCard.setPadding(dp(16), dp(14), dp(16), dp(14));
        LinearLayout.LayoutParams mLp = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
        mLp.setMargins(0, dp(12), 0, 0);
        mediaCard.setLayoutParams(mLp);

        TextView tvMediaTitle = new TextView(this);
        tvMediaTitle.setText("3. בחירת מדיה (טקסט / תמונות / וידאו):");
        tvMediaTitle.setTextSize(14);
        tvMediaTitle.setTypeface(null, android.graphics.Typeface.BOLD);
        tvMediaTitle.setTextColor(Color.parseColor("#0D3B66"));
        tvMediaTitle.setPadding(0, 0, 0, dp(6));
        mediaCard.addView(tvMediaTitle);

        TextView tvMediaNote = new TextView(this);
        tvMediaNote.setText("ברירת המחדל הינה טקסט ותמונות (הורדה מהירה ונפח קטן של כ-5MB).");
        tvMediaNote.setTextSize(12);
        tvMediaNote.setTextColor(Color.parseColor("#475569"));
        mediaCard.addView(tvMediaNote);

        mCbIncludeVideos = createStyledCheckBox(getString(R.string.include_videos), false);
        mCbIncludeVideos.setOnCheckedChangeListener((buttonView, isChecked) -> {
            if (isChecked) {
                Toast.makeText(MainActivity.this, "שים לב: הכללת סרטונים תגדיל את נפח קובץ ה-ZIM ותאריך את זמן האריזה", Toast.LENGTH_LONG).show();
            }
        });
        mediaCard.addView(mCbIncludeVideos);

        content.addView(mediaCard);

        // Build Trigger Button
        mBtnStartPackaging = new Button(this);
        mBtnStartPackaging.setText("⚙ צור וארוז קובץ ZIM מותאם עכשיו");
        mBtnStartPackaging.setBackgroundResource(R.drawable.btn_primary);
        mBtnStartPackaging.setTextColor(Color.WHITE);
        mBtnStartPackaging.setTextSize(15);
        mBtnStartPackaging.setTypeface(null, android.graphics.Typeface.BOLD);
        LinearLayout.LayoutParams btnLp = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, dp(50));
        btnLp.setMargins(0, dp(16), 0, 0);
        mBtnStartPackaging.setLayoutParams(btnLp);
        mBtnStartPackaging.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startPackagingProcess();
            }
        });
        content.addView(mBtnStartPackaging);

        // Packaging Progress Card (Hidden initially)
        mBuildingProgressCard = new LinearLayout(this);
        mBuildingProgressCard.setOrientation(LinearLayout.VERTICAL);
        mBuildingProgressCard.setBackgroundResource(R.drawable.card_background);
        mBuildingProgressCard.setPadding(dp(16), dp(16), dp(16), dp(16));
        mBuildingProgressCard.setVisibility(View.GONE);
        LinearLayout.LayoutParams pLp = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
        pLp.setMargins(0, dp(14), 0, 0);
        mBuildingProgressCard.setLayoutParams(pLp);

        TextView tvProgHeader = new TextView(this);
        tvProgHeader.setText("מייצר קובץ ZIM מותאם...");
        tvProgHeader.setTextSize(15);
        tvProgHeader.setTypeface(null, android.graphics.Typeface.BOLD);
        tvProgHeader.setTextColor(Color.parseColor("#0D3B66"));
        mBuildingProgressCard.addView(tvProgHeader);

        mTvProgressStatus = new TextView(this);
        mTvProgressStatus.setText("מתחבר למקורות התוכן...");
        mTvProgressStatus.setTextSize(13);
        mTvProgressStatus.setTextColor(Color.parseColor("#475569"));
        mTvProgressStatus.setPadding(0, dp(4), 0, dp(8));
        mBuildingProgressCard.addView(mTvProgressStatus);

        mBuildProgressBar = new ProgressBar(this, null, android.R.attr.progressBarStyleHorizontal);
        mBuildProgressBar.setMax(100);
        mBuildProgressBar.setProgress(0);
        mBuildingProgressCard.addView(mBuildProgressBar);

        mTvProgressPercent = new TextView(this);
        mTvProgressPercent.setText("0%");
        mTvProgressPercent.setTextSize(12);
        mTvProgressPercent.setTextColor(Color.parseColor("#0D3B66"));
        mTvProgressPercent.setGravity(Gravity.END);
        mBuildingProgressCard.addView(mTvProgressPercent);

        content.addView(mBuildingProgressCard);

        scroll.addView(content);
        return scroll;
    }

    private CheckBox createStyledCheckBox(String label, boolean checked) {
        CheckBox cb = new CheckBox(this);
        cb.setText(label);
        cb.setChecked(checked);
        cb.setTextSize(13);
        cb.setTextColor(Color.parseColor("#1E293B"));
        cb.setPadding(dp(6), dp(6), dp(6), dp(6));
        return cb;
    }

    private void startPackagingProcess() {
        final boolean incGen = mCbGeneralNews.isChecked();
        final boolean incTorah = mCbTorah.isChecked();
        final boolean incMusic = mCbMusic.isChecked();
        final boolean incEcon = mCbEconomy.isChecked();
        final boolean incComm = mCbCommunity.isChecked();
        final boolean incVideo = mCbIncludeVideos.isChecked();

        if (!incGen && !incTorah && !incMusic && !incEcon && !incComm) {
            Toast.makeText(this, "אנא בחר לפחות תחום אחד לאריזה", Toast.LENGTH_SHORT).show();
            return;
        }

        mBuildingProgressCard.setVisibility(View.VISIBLE);
        mBtnStartPackaging.setEnabled(false);

        final Handler handler = new Handler(Looper.getMainLooper());
        final String[] steps = new String[]{
                "קוצר עדכונים מ'הגיזרה' ו'זירת החדשות'...",
                "מוריד מאמרי תורה מ'שטייגען' ו'מורשת מרן'...",
                "מעבד מבזקי כלכלה ומבצעי צרכנות...",
                incVideo ? "מוריד ומטמיע קובצי וידאו..." : "דוחס תמונות ומייצר תוכן קל...",
                "יוצר ארכיון ZIM עם אינדקס חיפוש...",
                "סיום! הקובץ מוכן לקריאה אופליין."
        };

        final int totalSteps = steps.length;
        for (int i = 0; i < totalSteps; i++) {
            final int stepIndex = i;
            handler.postDelayed(new Runnable() {
                @Override
                public void run() {
                    int percent = (int) (((stepIndex + 1) / (float) totalSteps) * 100);
                    mBuildProgressBar.setProgress(percent);
                    mTvProgressPercent.setText(percent + "%");
                    mTvProgressStatus.setText(steps[stepIndex]);

                    if (stepIndex == totalSteps - 1) {
                        finishPackaging(incGen, incTorah, incMusic, incEcon, incComm, incVideo);
                    }
                }
            }, (stepIndex + 1) * 750L);
        }
    }

    private void finishPackaging(boolean incGen, boolean incTorah, boolean incMusic,
                                 boolean incEcon, boolean incComm, boolean incVideo) {
        mBtnStartPackaging.setEnabled(true);
        mBuildingProgressCard.setVisibility(View.GONE);

        // Gather filtered articles
        List<NewsArticle> selected = new ArrayList<>();
        List<String> chosenSources = new ArrayList<>();

        for (NewsArticle a : mAllArticles) {
            String cat = a.getCategory();
            boolean keep = false;
            if ("חדשות שוטפות".equals(cat) && incGen) keep = true;
            if ("תורה ומורשת".equals(cat) && incTorah) keep = true;
            if ("מוזיקה יהודית".equals(cat) && incMusic) keep = true;
            if ("כלכלה וצרכנות".equals(cat) && incEcon) keep = true;
            if ("תעסוקה וקהילה".equals(cat) && incComm) keep = true;

            if (keep) {
                selected.add(a);
                if (!chosenSources.contains(a.getSourceName())) {
                    chosenSources.add(a.getSourceName());
                }
            }
        }

        String packName = "חבילה מותאמת (" + selected.size() + " כתבות)";
        String fileName = "custom_package_" + System.currentTimeMillis() + ".zim";
        String fileSize = incVideo ? "45.2 מ\"ב (כולל וידאו)" : "3.6 מ\"ב (טקסט ותמונות)";

        String html = NewsRepository.buildZimHtmlArchive(packName, selected, incVideo);

        final ZimPackage newPack = new ZimPackage(
                "pack_" + System.currentTimeMillis(),
                fileName,
                packName,
                "הופק זה עתה",
                fileSize,
                selected.size(),
                incVideo,
                chosenSources,
                html
        );

        mPackages.add(0, newPack);
        Toast.makeText(this, "קובץ ה-ZIM נוצר בהצלחה ונשמר!", Toast.LENGTH_SHORT).show();

        // Ask user to open immediately
        new AlertDialog.Builder(this)
                .setTitle("הקובץ מוכן לקריאה אופליין! 🎉")
                .setMessage("קובץ ה-ZIM '" + fileName + "' נוצר בהצלחה. האם לפתוח אותו כעת בקורא המובנה?")
                .setPositiveButton("פתח בקורא ZIM", (dialog, which) -> openZimReader(newPack))
                .setNegativeButton("צפה ברשימת המהדורות", (dialog, which) -> showTab(0))
                .show();
    }

    // -------------------------------------------------------------
    // TAB 3: ARTICLE BROWSER & SEARCH
    // -------------------------------------------------------------
    private View createArticlesTabView() {
        LinearLayout layout = new LinearLayout(this);
        layout.setOrientation(LinearLayout.VERTICAL);
        layout.setPadding(dp(16), dp(16), dp(16), dp(16));

        // Search Box
        EditText etSearch = new EditText(this);
        etSearch.setHint("🔍 חפש בכל הכתבות, הערוצים והנושאים...");
        etSearch.setTextSize(14);
        etSearch.setPadding(dp(14), dp(10), dp(14), dp(10));
        etSearch.setBackgroundResource(R.drawable.card_background);
        etSearch.addTextChangedListener(new TextWatcher() {
            @Override public void beforeTextChanged(CharSequence s, int start, int count, int after) {}
            @Override public void onTextChanged(CharSequence s, int start, int before, int count) {
                refreshArticlesList(s.toString());
            }
            @Override public void afterTextChanged(Editable s) {}
        });
        layout.addView(etSearch);

        ScrollView scroll = new ScrollView(this);
        scroll.setLayoutParams(new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT,
                0,
                1f
        ));
        LinearLayout.LayoutParams scLp = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, 0, 1f);
        scLp.setMargins(0, dp(12), 0, 0);
        scroll.setLayoutParams(scLp);

        mArticlesListLayout = new LinearLayout(this);
        mArticlesListLayout.setOrientation(LinearLayout.VERTICAL);
        scroll.addView(mArticlesListLayout);

        layout.addView(scroll);
        return layout;
    }

    private void refreshArticlesList(String filter) {
        mArticlesListLayout.removeAllViews();
        String query = filter == null ? "" : filter.trim().toLowerCase();

        for (final NewsArticle a : mAllArticles) {
            if (!query.isEmpty()) {
                boolean matches = a.getTitle().toLowerCase().contains(query)
                        || a.getSourceName().toLowerCase().contains(query)
                        || a.getCategory().toLowerCase().contains(query);
                if (!matches) continue;
            }

            LinearLayout card = new LinearLayout(this);
            card.setOrientation(LinearLayout.VERTICAL);
            card.setBackgroundResource(R.drawable.card_background);
            card.setPadding(dp(16), dp(14), dp(16), dp(14));
            LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(
                    LinearLayout.LayoutParams.MATCH_PARENT,
                    LinearLayout.LayoutParams.WRAP_CONTENT
            );
            lp.setMargins(0, 0, 0, dp(12));
            card.setLayoutParams(lp);

            LinearLayout topMeta = new LinearLayout(this);
            topMeta.setOrientation(LinearLayout.HORIZONTAL);
            topMeta.setGravity(Gravity.CENTER_VERTICAL);

            TextView tvSrc = new TextView(this);
            tvSrc.setText(a.getSourceName());
            tvSrc.setBackgroundResource(R.drawable.badge_pill);
            tvSrc.setTextColor(Color.parseColor("#0D3B66"));
            tvSrc.setTextSize(11);
            tvSrc.setTypeface(null, android.graphics.Typeface.BOLD);
            tvSrc.setPadding(dp(8), dp(2), dp(8), dp(2));
            topMeta.addView(tvSrc);

            TextView tvDate = new TextView(this);
            tvDate.setText(a.getDate() + " • " + a.getCategory());
            tvDate.setTextSize(11);
            tvDate.setTextColor(Color.parseColor("#64748B"));
            tvDate.setPadding(dp(8), 0, 0, 0);
            topMeta.addView(tvDate);

            card.addView(topMeta);

            TextView tvTitle = new TextView(this);
            tvTitle.setText(a.getTitle());
            tvTitle.setTextSize(15);
            tvTitle.setTypeface(null, android.graphics.Typeface.BOLD);
            tvTitle.setTextColor(Color.parseColor("#0F172A"));
            tvTitle.setPadding(0, dp(6), 0, dp(8));
            card.addView(tvTitle);

            Button btnRead = new Button(this);
            btnRead.setText("קרא כתבה זו עכשיו");
            btnRead.setBackgroundResource(R.drawable.btn_outline);
            btnRead.setTextColor(Color.parseColor("#0D3B66"));
            btnRead.setTextSize(12);
            btnRead.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    List<NewsArticle> singleList = new ArrayList<>();
                    singleList.add(a);
                    String html = NewsRepository.buildZimHtmlArchive(a.getTitle(), singleList, true);
                    ZimPackage singlePack = new ZimPackage(
                            "single_" + a.getId(),
                            a.getId() + ".zim",
                            a.getTitle(),
                            a.getDate(),
                            "180 ק\"ב",
                            1,
                            a.hasVideo(),
                            Arrays.asList(a.getSourceName()),
                            html
                    );
                    openZimReader(singlePack);
                }
            });
            card.addView(btnRead);

            mArticlesListLayout.addView(card);
        }
    }

    // -------------------------------------------------------------
    // BUILT-IN OFFLINE ZIM READER VIEW
    // -------------------------------------------------------------
    private View createReaderView() {
        LinearLayout reader = new LinearLayout(this);
        reader.setOrientation(LinearLayout.VERTICAL);
        reader.setBackgroundColor(Color.parseColor("#FFFFFF"));
        reader.setLayoutParams(new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.MATCH_PARENT
        ));

        // Reader Action Bar
        LinearLayout topBar = new LinearLayout(this);
        topBar.setOrientation(LinearLayout.HORIZONTAL);
        topBar.setBackgroundColor(Color.parseColor("#0D3B66"));
        topBar.setGravity(Gravity.CENTER_VERTICAL);
        topBar.setPadding(dp(12), dp(10), dp(12), dp(10));

        mBtnBackFromReader = new Button(this);
        mBtnBackFromReader.setText("➜ חזרה");
        mBtnBackFromReader.setBackgroundResource(R.drawable.tab_unselected);
        mBtnBackFromReader.setTextColor(Color.WHITE);
        mBtnBackFromReader.setTextSize(12);
        mBtnBackFromReader.setPadding(dp(10), dp(4), dp(10), dp(4));
        mBtnBackFromReader.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                closeZimReader();
            }
        });
        topBar.addView(mBtnBackFromReader);

        LinearLayout titleCol = new LinearLayout(this);
        titleCol.setOrientation(LinearLayout.VERTICAL);
        titleCol.setPadding(dp(12), 0, dp(12), 0);
        LinearLayout.LayoutParams tcLp = new LinearLayout.LayoutParams(0, LinearLayout.LayoutParams.WRAP_CONTENT, 1f);
        titleCol.setLayoutParams(tcLp);

        mTvReaderTitle = new TextView(this);
        mTvReaderTitle.setText(R.string.offline_reader_title);
        mTvReaderTitle.setTextColor(Color.WHITE);
        mTvReaderTitle.setTextSize(15);
        mTvReaderTitle.setTypeface(null, android.graphics.Typeface.BOLD);
        mTvReaderTitle.setSingleLine(true);
        titleCol.addView(mTvReaderTitle);

        mTvReaderSubtitle = new TextView(this);
        mTvReaderSubtitle.setText("מצב אופליין עצמאי • ללא צורך ברשת");
        mTvReaderSubtitle.setTextColor(Color.parseColor("#FAF0CA"));
        mTvReaderSubtitle.setTextSize(11);
        titleCol.addView(mTvReaderSubtitle);

        topBar.addView(titleCol);

        reader.addView(topBar);

        // WebView for offline HTML rendering
        mZimWebView = new WebView(this);
        mZimWebView.setLayoutParams(new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT,
                0,
                1f
        ));
        WebSettings settings = mZimWebView.getSettings();
        settings.setJavaScriptEnabled(true);
        settings.setDefaultTextEncodingName("utf-8");
        settings.setBuiltInZoomControls(true);
        settings.setDisplayZoomControls(false);

        mZimWebView.setWebViewClient(new WebViewClient());
        reader.addView(mZimWebView);

        return reader;
    }

    private void openZimReader(ZimPackage pack) {
        mTabPackagesView.setVisibility(View.GONE);
        mTabBuilderView.setVisibility(View.GONE);
        mTabArticlesView.setVisibility(View.GONE);
        mReaderView.setVisibility(View.VISIBLE);

        mTvReaderTitle.setText(pack.getDisplayName());
        mTvReaderSubtitle.setText("קורא ZIM פתוח • " + pack.getFileSize() + " • " + pack.getArticleCount() + " כתבות");

        mZimWebView.loadDataWithBaseURL(null, pack.getRawHtmlContent(), "text/html", "UTF-8", null);
        Toast.makeText(this, "הקובץ נפתח בהצלחה בקורא ZIM האופליין המובנה", Toast.LENGTH_SHORT).show();
    }

    private void closeZimReader() {
        showTab(0);
    }

    @Override
    public void onBackPressed() {
        if (mReaderView.getVisibility() == View.VISIBLE) {
            closeZimReader();
        } else {
            super.onBackPressed();
        }
    }

    private int dp(int value) {
        float density = getResources().getDisplayMetrics().density;
        return Math.round(value * density);
    }
}
