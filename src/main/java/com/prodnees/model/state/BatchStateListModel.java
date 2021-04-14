package com.prodnees.model.state;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.prodnees.domain.enums.BatchStatus;
import com.prodnees.util.FormatUtil;
import java.time.LocalDate;

public class BatchStateListModel {

    private int id;
    private int productId;
    private String name;
    private String description;
    private BatchStatus status;
    @JsonFormat(pattern = FormatUtil.DATE)
    private LocalDate createdDate;
}
