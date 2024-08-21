package ifs.com.rssapi.Controller;
import ifs.com.rssapi.Dto.PreferenciaDTO;
import ifs.com.rssapi.Dto.UsuarioPreferenciasDTO;
import ifs.com.rssapi.Model.PreferenciaEntity;
import ifs.com.rssapi.Service.PreferenciaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/preferencias")
public class PreferenciaController {
    @Autowired
    private PreferenciaService preferenciaService;


    @PostMapping("/salvar")
    public ResponseEntity<PreferenciaEntity> salvarPreferencia(@RequestBody PreferenciaDTO preferenciaDTO) {
        PreferenciaEntity preferencia = preferenciaService.salvarPreferencia(preferenciaDTO);
        return ResponseEntity.ok(preferencia);
    }

    @GetMapping("/{usuarioId}")
    public ResponseEntity<UsuarioPreferenciasDTO> getUsuarioPreferencias(@PathVariable Long usuarioId) {
        UsuarioPreferenciasDTO usuarioPreferencias = preferenciaService.getUsuarioPreferencias(usuarioId);
        return ResponseEntity.ok(usuarioPreferencias);
    }




}


