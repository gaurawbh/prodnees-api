package com.prodnees.core.domain.doc;

import com.google.common.collect.ImmutableList;

import java.util.List;

public enum DocTypeEnum {
    Batch(ImmutableList.of(DocSubType.ApprovalDocument, DocSubType.SupportingDocument)),
    Stage(ImmutableList.of(DocSubType.ApprovalDocument, DocSubType.SupportingDocument)),
    Product(ImmutableList.of(DocSubType.ApprovalDocument, DocSubType.SupportingDocument));
    private List<DocSubType> docSubTypeList;

    DocTypeEnum(List<DocSubType> docSubTypeList) {
        this.docSubTypeList = docSubTypeList;

    }

}


