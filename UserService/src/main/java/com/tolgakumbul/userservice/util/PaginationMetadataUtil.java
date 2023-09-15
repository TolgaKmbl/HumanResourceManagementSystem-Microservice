package com.tolgakumbul.userservice.util;

import com.tolgakumbul.userservice.model.common.PageableDTO;
import com.tolgakumbul.userservice.model.common.PaginationMetadataDTO;

public class PaginationMetadataUtil {

    public static PaginationMetadataDTO getPaginationMetadata(Long totalRowCount, PageableDTO pageableDTO) {
        PaginationMetadataDTO paginationMetadataDTO = new PaginationMetadataDTO();
        paginationMetadataDTO.setTotalRowCount(totalRowCount);
        paginationMetadataDTO.setCurrentPage(pageableDTO.getPageNumber());
        paginationMetadataDTO.setPageSize(pageableDTO.getPageSize());
        paginationMetadataDTO.setHasPreviousPage(pageableDTO.getPageNumber() > 1 && totalRowCount > 0);
        paginationMetadataDTO.setHasNextPage((pageableDTO.getPageNumber() * pageableDTO.getPageSize()) < totalRowCount);
        return paginationMetadataDTO;
    }

}
