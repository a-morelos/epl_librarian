package org.greeneyed.epl.librarian.controller;

import java.time.Instant;
import java.time.ZoneId;

import org.greeneyed.epl.librarian.services.PreferencesService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.http.ResponseEntity.BodyBuilder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import lombok.Data;
import lombok.RequiredArgsConstructor;

@RestController
@Data
@RequestMapping(value = "/librarian/preferences")
@RequiredArgsConstructor(onConstructor = @__({ @Autowired }))
public class PreferencesAPIController {

	private static final BodyBuilder OK_BUILDER = ResponseEntity.ok();

	private final PreferencesService preferencesService;

	@GetMapping(value = "/fecha_base")
	public ResponseEntity<Long> fechaBase() {
		return preferencesService.getFechaBase()
				.map(ldt -> ldt.atStartOfDay(ZoneId.systemDefault()).toInstant().toEpochMilli())
				.map(ResponseEntity::ok)
				.orElseGet(OK_BUILDER::build);
	}

	@PostMapping(value = "/fecha_base")
	public ResponseEntity<String> guardarFechaBase(@RequestParam(name = "fechaBase") long fechaBaseLong) {
		preferencesService
				.setFechaBase(Instant.ofEpochMilli(fechaBaseLong).atZone(ZoneId.systemDefault()).toLocalDate());
		return OK_BUILDER.build();
	}
}