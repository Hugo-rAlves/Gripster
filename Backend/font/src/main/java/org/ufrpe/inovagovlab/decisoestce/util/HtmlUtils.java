package org.ufrpe.inovagovlab.decisoestce.util;

import org.jsoup.Jsoup;
import org.jsoup.safety.Safelist;

public class HtmlUtils {

    public static String clean(String text){
        return Jsoup.clean(text, "", Safelist.none());
    }

    /**
     * Limpar o HTML colocando a acentuação correta
     * Tabela usada https://erikasarti.com/html/acentuacao-caracteres-especiais/
     */

    public static String cleanAccentuation(String text) {
        String cleaned = text.replace("&amp", "&") //it MUST be the first!!!
                .replace("&aacute;", "á")
                .replace("&Aacute;", "Á")
                .replace("&iacute;", "í")
                .replace("&Iacute;", "Í")
                .replace("&atilde;", "ã")
                .replace("&Atilde;", "Ã")
                .replace("&oacute;", "ó")
                .replace("&Oacute;", "Ó")
                .replace("&acirc;", "â")
                .replace("&Acirc;", "Â")
                .replace("&otilde;", "õ")
                .replace("&Otilde;", "Õ")
                .replace("&agrave;", "à")
                .replace("&Agrave;", "À")
                .replace("&ocirc;", "ô")
                .replace("&Ocirc;", "Ô")
                .replace("&eacute;", "é")
                .replace("&Eacute;", "É")
                .replace("&uacute;", "ú")
                .replace("&Uacute;", "Ú")
                .replace("&ecirc;", "ê")
                .replace("&Ecirc;", "Ê")
                .replace("&ccedil;", "ç")
                .replace("&Ccedil;", "Ç")
                .replace("&ordm;", "º")
                .replace("&ordf;", "ª")
                .replace("&hellip;", "…") //Retiçências NÃO são 3 pontos. É um caracter individual
                .replace("&ldquo;", "“")
                .replace("&rdquo;", "”")
                .replace("&lsquo;", "‘")
                .replace("&rsquo;", "’")
                .replace("&ndash;", "–")
                .replace("&mdash;", "—")
                .replace("&bull;", "•")
                .replace("&nbsp;", " ")
                .replace("&sect;", "§")
                .replace("&lt;", "<")
                .replace("&gt;", ">")
                .replace("&le;", "≤")
                .replace("&ge;", "≥")
                .replace("  ", " ")
                .replace("&deg;", "º")
                ;
        return cleaned;

    }
}
