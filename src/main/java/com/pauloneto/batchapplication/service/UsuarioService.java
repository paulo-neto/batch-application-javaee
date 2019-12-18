package com.pauloneto.batchapplication.service;

import java.lang.reflect.InvocationTargetException;
import java.util.List;
import java.util.Objects;

import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.Query;
import javax.transaction.Transactional;
import javax.transaction.Transactional.TxType;

import com.pauloneto.batchapplication.messaging.producer.EmailProducer;
import com.pauloneto.batchapplication.models.Perfil;
import com.pauloneto.batchapplication.models.Usuario;
import com.pauloneto.batchapplication.repository.PerfilRepository;
import com.pauloneto.batchapplication.repository.UsuarioRepository;
import org.apache.commons.beanutils.BeanUtils;

/**
* @author paulo.antonio@fornecedores.sicoob.com.br 
* @version 0.1 - 29 de out de 2019
*/
public class UsuarioService {

	@Inject
	private UsuarioRepository usuarioRepository;

	@Inject
	private PerfilRepository perfilRepository;

	@Inject
	private EntityManager em;

	@Inject
	private EmailProducer emailProducer;

	@Transactional(value = TxType.REQUIRED,rollbackOn = {RuntimeException.class, Exception.class})
	public Usuario save(Usuario usuario) {
		usuario = usuarioRepository.save(usuario);
		emailProducer.sendEmail(usuario);
		return usuario;
	}

	@Transactional(value = TxType.REQUIRED,rollbackOn = {RuntimeException.class, Exception.class})
    public void remove(Long id) {
		Usuario usuBD = usuarioRepository.findBy(id);
		if(Objects.isNull(usuBD))
			throw new IllegalArgumentException("Usuário não encontrado!");

	    usuarioRepository.attachAndRemove(usuBD);
    }

	@Transactional(value = TxType.REQUIRED,rollbackOn = {RuntimeException.class, Exception.class})
    public void edit(Long id, Usuario usuario) throws IllegalAccessException, InvocationTargetException {
	    Usuario usuBD = usuarioRepository.findBy(id);
	    if(Objects.isNull(usuBD))
	        throw new IllegalArgumentException("Usuário não encontrado!");

		BeanUtils.copyProperties(usuBD,usuario);
		usuBD.setId(id);
		usuarioRepository.save(usuBD);
    }

	public List<Usuario> fildAll() {
		return usuarioRepository.findAll();
	}

    public Usuario findById(Long id) {
		Usuario usuBD = usuarioRepository.findBy(id);
		if(Objects.isNull(usuBD))
			throw new IllegalArgumentException("Usuário não encontrado!");

		return usuBD;
    }

	@Transactional(value = TxType.REQUIRED,rollbackOn = {RuntimeException.class, Exception.class})
	public Usuario adicionaPerfilAUsuario(Long idPerfil, Long idUsuario) {
		Usuario usuBD = usuarioRepository.findBy(idUsuario);
		if(Objects.isNull(usuBD))
			throw new IllegalArgumentException("Usuário não encontrado!");

		Perfil perfilBD = perfilRepository.findBy(idPerfil);
		if(Objects.isNull(perfilBD))
			throw new IllegalArgumentException("Perfil não encontrado!");

		usuBD.getPerfis().add(perfilBD);
		return usuBD;
	}

	public List<Perfil> listarPerfisUsuario(Long idUsuario) {
		Query query = em.createQuery("select u.perfis from Usuario u where u.id = :id");
		query.setParameter("id",idUsuario);
		return query.getResultList();
	}

	@Transactional(value = TxType.REQUIRED,rollbackOn = {RuntimeException.class, Exception.class})
	public Usuario removePerfilUsuario(Long idPerfil, Long idUsuario) {
		Usuario usuBD = usuarioRepository.findBy(idUsuario);
		if(Objects.isNull(usuBD))
			throw new IllegalArgumentException("Usuário não encontrado!");

		Perfil perfilBD = perfilRepository.findBy(idPerfil);
		if(Objects.isNull(perfilBD))
			throw new IllegalArgumentException("Perfil não encontrado!");

		usuBD.getPerfis().remove(perfilBD);
		return usuBD;
	}
}
