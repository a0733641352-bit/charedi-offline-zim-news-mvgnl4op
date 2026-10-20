package com.generated.app;

import java.util.ArrayList;
import java.util.List;

public class NewsRepository {

    public static List<NewsArticle> getAllInitialArticles() {
        List<NewsArticle> list = new ArrayList<>();

        list.add(new NewsArticle(
                "hgz_1",
                "הגיזרה",
                "https://hagizra.news/",
                "חדשות שוטפות",
                "דיווח מקיף: סיכום האירועים הביטחוניים והמדיניים ביממה האחרונה",
                "היום, 14:30",
                "<p>מערכת 'הגיזרה' מגישה סקירה נרחבת על כל ההתפתחויות בגזרות השונות, פעילות כוחות הביטחון ומאמצי ההיערכות לקראת שבת.</p>" +
                "<p>גורמים בכירים מוסרים כי נשמרת דריכות גבוהה בכל המרחבים. נרשמו תנועות משמעותיות בשטח וכן מעקב הדוק אחר תשתיות הגבול.</p>" +
                "<div style='background:#f0f7ff;padding:12px;border-right:4px solid #0d3b66;margin:12px 0;'><strong>מבזק קצר:</strong> תנועת התחבורה הבינעירונית שבה לסדרה לאחר בדיקות מוגברות בצמתים מרכזיים.</div>",
                "דסק הגיזרה",
                false,
                3
        ));

        list.add(new NewsArticle(
                "zrt_1",
                "זירת החדשות",
                "https://channel.zirat.news/",
                "חדשות שוטפות",
                "ההכרעה בוועדת הכספים: תקציבי הפיתוח לערי הפריפריה אושרו פה אחד",
                "היום, 13:15",
                "<p>דיון סוער בוועדת הכספים הסתיים בהסכמה כוללת להקצאת משאבים נרחבים לתשתיות עירוניות, תחבורה ציבורית והקמת מרכזי תעסוקה חדשים בריכוזים החרדיים והכלליים.</p>" +
                "<p>נציגי הסיעות ציינו לשבח את ההתגייסות המהירה לטובת ציבור התושבים והבטיחו מעקב צמוד אחר יישום ההחלטות בחודשים הקרובים.</p>",
                "כתב פרלמנטרי",
                false,
                4
        ));

        list.add(new NewsArticle(
                "hkav_1",
                "הקו החרדי",
                "https://channel.hakavhacharedi.com/",
                "חדשות שוטפות",
                "היערכות שיא בריכוזים החרדיים למעמדי התפילה ופתיחת זמן הלימודים",
                "היום, 12:00",
                "<p>אלפי בחורים ואברכים שבים אל בתי המדרשות והיכלי התורה. רבנים ומחנכים פנו באיגרת חיזוק מיוחדת לעידוד ההתמדה וקביעת עתים לתורה בשמחה ובמתינות.</p>" +
                "<p>רכבת אווירית של מבקשי ברכה ועצה פקדה את מעונם של גדולי ישראל במהלך השבוע האחרון לקראת תחילת הזמן החדש.</p>",
                "מערכת הקו החרדי",
                true,
                2
        ));

        list.add(new NewsArticle(
                "stg_1",
                "שטייגען",
                "https://ysiva.thechats.click/",
                "תורה ומורשת",
                "חידושי סוגיות: עיונים בסוגיית 'שניים אוחזין בטלית' ובדיני ממון",
                "אתמול, 20:45",
                "<p>מאמר תורני מרתק מאת ראשי הישיבות, המנתח בבהירות את יסודות החזקה, תפיסה בממון ושיטות הראשונים והאחרונים בסוגיה המרכזית הנלמדת בהיכלי הישיבות.</p>" +
                "<p>במאמר מובאות הוכחות מאירות עיניים מפסקי הרי\"ף, הרמב\"ם וגדולי הפוסקים המעמידות את יסוד הנאמנות באור חדש.</p>",
                "בית המדרש שטייגען",
                false,
                6
        ));

        list.add(new NewsArticle(
                "mzn_1",
                "מורשת מרן אונליין",
                "https://news.moreshet-maran.com/",
                "תורה ומורשת",
                "פנינים ופסקים חדשים מכתב ידו של מרן זצוק\"ל הרואים אור לראשונה",
                "היום, 11:20",
                "<p>מכון המאור חושף פנקסי הערות אישיים של מרן פוסק הדור, הכוללים הכרעות הלכתיות בהלכות שבת וברכות, לצד הנהגות מופת וביאורי אגדה נדירים שנכתבו בשולי ספריו.</p>" +
                "<p>חוקרי המכון מציינים את גודל הדיוק והעמקות בכל שורה וקו שהותיר אחריו רבנו זצ\"ל להאיר את עיני ישראל.</p>",
                "מכון מורשת מרן",
                false,
                5
        ));

        list.add(new NewsArticle(
                "msc_1",
                "מיוזיק אונליין",
                "https://music.chatfree.app/",
                "מוזיקה יהודית",
                "סינגל חדש ומרגש: 'קול תחינתי' כובש את תחנות השידור והיכלי הנגינה",
                "אתמול, 18:30",
                "<p>טובי האמנים והמעבדים התכנסו ליצירה משותפת מרטיטת לב הנוגעת במיתרי הנפש. השיר משלב מנגינה חסידית שורשית עם עיבוד מיתרים עכשווי ומוקפד.</p>" +
                "<p>האלבום המלא צפוי לראות אור אי\"ה לקראת חודש החגים בליווי קליפ מיוחד שצולם בעיר העתיקה.</p>",
                "דסק המוזיקה",
                true,
                3
        ));

        list.add(new NewsArticle(
                "exp_1",
                "מוזיקה אקספרס",
                "https://music-express.chatfree.app/",
                "מוזיקה יהודית",
                "מצעד ההשמעות השבועי: הלהיטים המובילים של עולם התורה והישיבות",
                "היום, 10:00",
                "<p>סיכום דירוג השירים שהושמעו ביותר בשמחות, קעמפים והיכלי המוזיקה: במקום הראשון שיר הנושא 'אשרי העם', ואחריו ביצועי אקפלה מרהיבים.</p>",
                "מוזיקה אקספרס",
                false,
                2
        ));

        list.add(new NewsArticle(
                "mes_1",
                "טיפים לכלכלה נכונה",
                "https://mesudarim.chatfree.app/",
                "כלכלה וצרכנות",
                "מדריך מעשי: כך תחסכו עד 1,200 ש\"ח בחודש בהוצאות הקניות לבית",
                "היום, 09:15",
                "<p>1. תכנון שבועי מדויק של תפריט הארוחות לפני היציאה לסופרמרקט.<br/>" +
                "2. השוואת סל מוצרים דיגיטלית באמצעות רשימות סגורות.<br/>" +
                "3. רכישת מותגים איכותיים חלופיים והימנעות מקנייה דחופה בפיצוציות או חנויות נוחות יקרות.</p>" +
                "<p>יועצי כלכלת המשפחה מציגים מחשבון פשוט המאפשר מעקב צמוד אחרי התקציב המשפחתי ללא כאב ראש.</p>",
                "צוות מסודרים",
                false,
                4
        ));

        list.add(new NewsArticle(
                "ali_1",
                "My ali - מותגים ומציאות",
                "https://myali.chatfree.app/",
                "כלכלה וצרכנות",
                "ריכוז קופונים בלעדיים למכשירי חשמל, גאדג'טים ומוצרי בית שימושיים",
                "היום, 08:30",
                "<p>סקירת המבצעים הכדאיים ביותר ברשת: מטענים מהירים, שואבים ניידים, פנסי שבת מעוצבים ובגדי ילדים איכותיים במחירי עלות ישירות מהיצרן.</p>",
                "צוות My Ali",
                false,
                2
        ));

        list.add(new NewsArticle(
                "avd_1",
                "עבודות איכות לחרדים",
                "https://avodot-eichut.chatfree.app/",
                "תעסוקה וקהילה",
                "עשרות משרות חדשות בהייטק, הנהלת חשבונות ופיתוח תוכנה מותאמות לציבור",
                "אתמול, 16:40",
                "<p>חברות טכנולוגיה מובילות פותחות מחלקות ייעודיות באווירה שמורה, עם שעות עבודה גמישות ושכר מתגמל במיוחד. דרושים מתכנתים, אנשי QA ומנהלי פרויקטים.</p>",
                "לוח תעסוקה",
                false,
                3
        ));

        list.add(new NewsArticle(
                "mysh_1",
                "מיין שמש my-shemesh",
                "https://myshemesh.co.il/",
                "תעסוקה וקהילה",
                "חדשות בית שמש: הרחבת מערך התחבורה הבינעירונית וחנוכת גני משחקים חדשים",
                "היום, 12:45",
                "<p>עיריית בית שמש חנכה הבוקר שני מתחמי פנאי רחבי ידיים ברמת בית שמש ד', ופתחה קווי תחבורה ישירים נוספים לירושלים ולבני ברק.</p>",
                "מערכת מיין שמש",
                false,
                3
        ));

        list.add(new NewsArticle(
                "isr_1",
                "מעיינות ונחלים - ישראל שפירא",
                "https://channel.israelshapira.com/",
                "תורה ומורשת",
                "מסע היסטורי מצולם אל שרידי הישוב היהודי הקדום ומעיינות הרי יהודה",
                "אתמול, 14:10",
                "<p>החוקר הנודע ישראל שפירא יוצא למסע בעקבות מקורות המים ששימשו את עולי הרגל לירושלים בימי בית המקדש, ומביא תיאורים מרתקים וממצאים ארכאולוגיים מפעימים.</p>",
                "ישראל שפירא",
                true,
                5
        ));

        list.add(new NewsArticle(
                "auth_1",
                "אותנטי ניוז",
                "https://authenti.newsupdates.click/",
                "חדשות שוטפות",
                "תיעוד בלעדי: מבצע החסד הלילי שסייע למאות משפחות נזקקות לקראת השבת",
                "היום, 07:45",
                "<p>מתנדבים מכל רחבי הארץ שינעו חבילות מזון טריות, פירות, ירקות ומצרכי יסוד לבתיהם של חולים ומשפחות ברוכות ילדים. עדות חיה לערבות ההדדית של עם ישראל.</p>",
                "מערכת אותנטי ניוז",
                false,
                3
        ));

        return list;
    }

    public static String buildZimHtmlArchive(String packTitle, List<NewsArticle> articles, boolean includeVideos) {
        StringBuilder sb = new StringBuilder();
        sb.append("<!DOCTYPE html>\n")
          .append("<html dir='rtl' lang='he'>\n<head>\n")
          .append("<meta charset='utf-8'>\n")
          .append("<meta name='viewport' content='width=device-width, initial-scale=1.0'>\n")
          .append("<title>").append(packTitle).append("</title>\n")
          .append("<style>\n")
          .append("body { font-family: -apple-system, Roboto, sans-serif; background-color: #F8FAFC; color: #1E293B; margin: 0; padding: 16px; line-height: 1.6; direction: rtl; }\n")
          .append(".header { background: #0D3B66; color: white; padding: 20px; border-radius: 12px; margin-bottom: 20px; box-shadow: 0 4px 6px rgba(0,0,0,0.1); }\n")
          .append(".header h1 { margin: 0 0 6px 0; font-size: 22px; }\n")
          .append(".header .meta { font-size: 13px; opacity: 0.85; }\n")
          .append(".stats-badge { display: inline-block; background: #EE964B; color: white; padding: 4px 10px; border-radius: 14px; font-weight: bold; font-size: 12px; margin-top: 6px; }\n")
          .append(".article-card { background: white; border-radius: 12px; padding: 18px; margin-bottom: 18px; border: 1px solid #E2E8F0; box-shadow: 0 2px 4px rgba(0,0,0,0.03); }\n")
          .append(".article-meta { font-size: 12px; color: #64748B; margin-bottom: 8px; display: flex; justify-content: space-between; align-items: center; }\n")
          .append(".source-tag { background: #FAF0CA; color: #7B341E; padding: 3px 8px; border-radius: 6px; font-weight: bold; }\n")
          .append(".article-title { font-size: 19px; font-weight: 700; color: #0D3B66; margin: 6px 0 12px 0; }\n")
          .append(".article-body { font-size: 15px; color: #334155; line-height: 1.7; }\n")
          .append(".video-box { background: #F1F5F9; border-radius: 8px; padding: 12px; margin-top: 10px; border-right: 4px solid #EE964B; font-size: 13px; color: #0F172A; }\n")
          .append(".footer { text-align: center; font-size: 12px; color: #94A3B8; margin-top: 30px; padding: 20px 0; border-top: 1px solid #E2E8F0; }\n")
          .append("</style>\n</head>\n<body>\n");

        sb.append("<div class='header'>\n")
          .append("<h1>").append(packTitle).append("</h1>\n")
          .append("<div class='meta'>נארז לחבילת ZIM בלחיצת כפתור לקריאה מלאה ללא אינטרנט</div>\n")
          .append("<span class='stats-badge'>").append(articles.size()).append(" כתבות ומבזקים</span>\n")
          .append("</div>\n");

        for (NewsArticle a : articles) {
            sb.append("<div class='article-card' id='art_").append(a.getId()).append("'>\n")
              .append("<div class='article-meta'>\n")
              .append("<span class='source-tag'>").append(a.getSourceName()).append("</span>\n")
              .append("<span>").append(a.getDate()).append(" | ").append(a.getAuthor()).append("</span>\n")
              .append("</div>\n")
              .append("<h2 class='article-title'>").append(a.getTitle()).append("</h2>\n")
              .append("<div class='article-body'>").append(a.getContentHtml()).append("</div>\n");

            if (a.hasVideo()) {
                if (includeVideos) {
                    sb.append("<div class='video-box'>🎬 <strong>קובץ וידאו מקומי מוטמע:</strong> הסרטון נארז במלואו בארכיון וזמין לצפייה אופליין ללא רשת.</div>\n");
                } else {
                    sb.append("<div class='video-box'>🎥 <em>הערת מדיה:</em> הכתבה מכילה סרטון מקוון שהושמט לצורך חיסכון בנפח אופליין לפי בחירתך.</div>\n");
                }
            }
            sb.append("</div>\n");
        }

        sb.append("<div class='footer'>\n")
          .append("מערכת ארכיון וקורא ZIM אופליין • שמור במכשיר לשימוש במצב טיסה או אזורים ללא קליטה\n")
          .append("</div>\n")
          .append("</body>\n</html>");

        return sb.toString();
    }
}
