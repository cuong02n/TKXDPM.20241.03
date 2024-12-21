package com.cuong02n.aimsbackend.repository;

import com.cuong02n.aimsbackend.model.entity.Invoice;
import org.springframework.data.jpa.repository.JpaRepository;

public interface InvoiceRepository extends JpaRepository<Invoice,Long> {

}
