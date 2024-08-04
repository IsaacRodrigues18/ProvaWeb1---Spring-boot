package ifs.com.rssapi.Controller;

import ifs.com.rssapi.Model.NoticiaEntity;
import ifs.com.rssapi.Service.NoticiaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/noticias")
public class NoticiaController {

    @Autowired
    private NoticiaService noticiaService;


    @GetMapping("/todas")
    public ResponseEntity<List<NoticiaEntity>> getTodasNoticias() {
        List<NoticiaEntity> todasNoticias = noticiaService.listarTodasNoticias();
        return ResponseEntity.ok(todasNoticias);
    }

    @GetMapping("/recentes")
    public ResponseEntity<List<NoticiaEntity>> getNoticiasRecentes() {
        List<NoticiaEntity> noticiasRecentes = noticiaService.listarMaisRecentesNoticias();
        return ResponseEntity.ok(noticiasRecentes);
    }

    @GetMapping("/{id}")
    public ResponseEntity<NoticiaEntity> getNoticiaById(@PathVariable Long id) {
        Optional<NoticiaEntity> noticiaOpt = noticiaService.buscarNoticiaPorId(id);
        return noticiaOpt.map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }






}

