package ifs.com.rssapi.Service;
import ifs.com.rssapi.Model.CategoriaEntity;
import ifs.com.rssapi.Model.NoticiaEntity;
import ifs.com.rssapi.Repository.CategoriaRepository;
import ifs.com.rssapi.Repository.NoticiaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import java.net.URL;
import java.time.LocalDateTime;
import java.time.OffsetDateTime;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;


@Component
public class RssFeedDownloader {

    @Autowired
    private CategoriaRepository categoriaRepository;

    @Autowired
    private NoticiaRepository noticiaRepository;

    @Scheduled(fixedRate = 3600000) // 1 hora
    public void downloadRssFeeds() {
        // URLs dos feeds RSS do G1 por categoria
        Map<String, String> rssUrls = new HashMap<>();
        rssUrls.put("Carros", "https://g1.globo.com/dynamo/carros/rss2.xml");
        rssUrls.put("Ciência e Saúde", "https://g1.globo.com/dynamo/ciencia-e-saude/rss2.xml");
        rssUrls.put("Concursos e Emprego", "https://g1.globo.com/dynamo/concursos-e-emprego/rss2.xml");
        rssUrls.put("Economia", "https://g1.globo.com/dynamo/economia/rss2.xml");
        rssUrls.put("Educação", "https://g1.globo.com/dynamo/educacao/rss2.xml");
        rssUrls.put("Loterias", "https://g1.globo.com/dynamo/loterias/rss2.xml");
        rssUrls.put("Mundo", "https://g1.globo.com/dynamo/mundo/rss2.xml");
        rssUrls.put("Música", "https://g1.globo.com/dynamo/musica/rss2.xml");
        rssUrls.put("Natureza", "https://g1.globo.com/dynamo/natureza/rss2.xml");
        rssUrls.put("Planeta Bizarro", "https://g1.globo.com/dynamo/planeta-bizarro/rss2.xml");
        rssUrls.put("Política", "https://g1.globo.com/dynamo/politica/mensalao/rss2.xml");
        rssUrls.put("Pop & Arte", "https://g1.globo.com/dynamo/pop-arte/rss2.xml");
        rssUrls.put("Tecnologia e Games", "https://g1.globo.com/dynamo/tecnologia/rss2.xml");
        rssUrls.put("Turismo e Viagem", "https://g1.globo.com/dynamo/turismo-e-viagem/rss2.xml");

        // Processa cada categoria
        rssUrls.forEach((categoriaNome, rssUrl) -> {
            CategoriaEntity categoria = categoriaRepository.findByNomeCategoria(categoriaNome)
                    .orElseGet(() -> new CategoriaEntity(categoriaNome, rssUrl));
            if (categoria.getIdCategoria() == null) {
                categoria = categoriaRepository.save(categoria);
            }
            processarRssFeedCategoria(categoria, rssUrl);
        });
    }

    private void processarRssFeedCategoria(CategoriaEntity categoria, String rssUrl) {
        try {
            URL url = new URL(rssUrl);
            DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
            DocumentBuilder builder = factory.newDocumentBuilder();
            org.w3c.dom.Document doc = ((DocumentBuilder) builder).parse(url.openStream());

            NodeList itemList = doc.getElementsByTagName("item");
            for (int i = 0; i < itemList.getLength(); i++) {
                Node node = itemList.item(i);
                if (node.getNodeType() == Node.ELEMENT_NODE) {
                    Element element = (Element) node;
                    String link = element.getElementsByTagName("link").item(0).getTextContent();

                    if (!noticiaRepository.existsByLink(link)) {
                        String titulo = element.getElementsByTagName("title").item(0).getTextContent();
                        String descricao = element.getElementsByTagName("description").item(0).getTextContent();
                        String pubDate = element.getElementsByTagName("pubDate").item(0).getTextContent();
                        String imagemUrl = extrairImagemUrl(descricao);

                        NoticiaEntity noticia = new NoticiaEntity();
                        noticia.setTitulo(titulo);
                        noticia.setLink(link);
                        noticia.setDescricao(descricao);
                        noticia.setDataPublicacao(parsePubDate(pubDate));
                        noticia.setImageUrl(imagemUrl);
                        noticia.setCategoria(categoria);

                        noticiaRepository.save(noticia);
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    private LocalDateTime parsePubDate(String pubDate) {
        try {
            // NOVO -> Tentar primeiro com ZonedDateTime
            DateTimeFormatter zonedFormatter = DateTimeFormatter.ofPattern("EEE, dd MMM yyyy HH:mm:ss zzz", Locale.ENGLISH);
            ZonedDateTime zonedDateTime = ZonedDateTime.parse(pubDate, zonedFormatter);
            return zonedDateTime.toLocalDateTime();
        } catch (DateTimeParseException e) {
            // Se falhar, tentar com OffsetDateTime
            try {
                DateTimeFormatter offsetFormatter = DateTimeFormatter.RFC_1123_DATE_TIME;
                OffsetDateTime offsetDateTime = OffsetDateTime.parse(pubDate, offsetFormatter);
                return offsetDateTime.toLocalDateTime();
            } catch (DateTimeParseException e2) {
                e2.printStackTrace();
                return LocalDateTime.now(); // Caso haja erro, retorna a data atual
            }
        }
    }
    private String extrairImagemUrl(String descricao) {
        int startIndex = descricao.indexOf("src=\"") + 5;
        int endIndex = descricao.indexOf("\"", startIndex);
        return (startIndex > 4 && endIndex > startIndex) ? descricao.substring(startIndex, endIndex) : null;
    }
}


