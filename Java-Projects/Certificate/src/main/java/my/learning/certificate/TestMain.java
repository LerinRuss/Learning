package my.learning.certificate;

import java.net.URL;
import java.security.cert.Certificate;
import java.security.cert.CertificateExpiredException;
import java.security.cert.X509Certificate;
import javax.net.ssl.HttpsURLConnection;

public class TestMain {
    public static void main(String[] args) throws Exception {
        testConnectionTo("https://oauth.vk.com/access_token");
    }

    public static void testConnectionTo(String rawUrl) throws Exception {
        URL destinationUrl = new URL(rawUrl);

        HttpsURLConnection conn = (HttpsURLConnection) destinationUrl.openConnection();
        conn.connect();

        Certificate[] certs = conn.getServerCertificates();
        for (Certificate cert : certs) {
            System.out.println("Certificate is: " + cert);
            if(cert instanceof X509Certificate) {
                try {
                    ((X509Certificate) cert).checkValidity();
                    System.out.println("Certificate is active for current date");
                } catch(CertificateExpiredException cee) {
                    System.out.println("Certificate is expired");
                }
            }
        }
    }
}
