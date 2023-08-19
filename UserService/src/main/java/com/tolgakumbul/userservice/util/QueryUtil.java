package com.tolgakumbul.userservice.util;

import com.tolgakumbul.userservice.dao.model.Pageable;
import org.apache.commons.lang3.StringUtils;

public class QueryUtil {

    public static StringBuilder addPageableQuery(StringBuilder sql, Pageable pageable) {
        if (pageable != null) {
            StringBuilder stringBuilder = new StringBuilder();

            if (!StringUtils.isBlank(pageable.getSortColumn()) && !StringUtils.isBlank(pageable.getSortType())) {
                stringBuilder.append(" ORDER BY " + pageable.getSortColumn() + " " + pageable.getSortType());
            }

            if (pageable.getPageNumber() != null && pageable.getPageSize() != null) {
                long offset = pageable.getPageNumber() > 0 ? ((pageable.getPageNumber() - 1) * pageable.getPageSize()) : 0;
                long endRow = offset + pageable.getPageSize();
                stringBuilder.append(" OFFSET " + offset + " ROWS FETCH NEXT " + (endRow - offset) + " ROWS ONLY");
            }
            sql.append(stringBuilder);
        }
        return sql;
    }

}
