package com.prodnees.dao.rels;

import com.prodnees.domain.rels.DocumentOwner;
import org.springframework.data.jpa.repository.JpaRepository;

import javax.swing.text.html.Option;
import java.util.List;
import java.util.Optional;

public interface DocumentOwnerDao extends JpaRepository<DocumentOwner, Integer> {
    Optional<DocumentOwner> findByDocumentIdAndOwnerId(int documentId, int ownerId);

    List<DocumentOwner> getAllByDocumentId(int documentId);

    List<DocumentOwner> getAllByOwnerId(int ownerId);
}
