package com.prodnees.core.domain.doc;

import com.google.common.collect.ImmutableList;

import java.util.List;

public enum DocType {
    Batch(ImmutableList.of(DocSubType.ApprovalDocument, DocSubType.SupportingDocument)),
    Stage(ImmutableList.of()),
    Product(ImmutableList.of());
    private    List<DocSubType> docSubTypeList;

    DocType(List<DocSubType> docSubTypeList) {

    }

}


