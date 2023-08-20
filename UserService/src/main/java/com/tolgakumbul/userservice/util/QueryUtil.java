package com.tolgakumbul.userservice.util;

import com.tolgakumbul.userservice.dao.model.Pageable;
import org.apache.commons.lang3.StringUtils;

import java.util.List;

public class QueryUtil {

    public static StringBuilder addPageableQuery(StringBuilder sql, List<Object> params, Pageable pageable) {
        if (pageable != null) {
            StringBuilder stringBuilder = new StringBuilder();

            if (!StringUtils.isBlank(pageable.getSortColumn()) &&
                    (!StringUtils.isBlank(pageable.getSortType()) &&
                            ("ASC".equalsIgnoreCase(pageable.getSortType()) || "DESC".equalsIgnoreCase(pageable.getSortType()))
                    )
            ) {
                stringBuilder.append(" ORDER BY ? "+ pageable.getSortType());
                params.add(pageable.getSortColumn());
            }

            if (pageable.getPageNumber() != null && pageable.getPageSize() != null) {
                long offset = pageable.getPageNumber() > 0 ? ((pageable.getPageNumber() - 1) * pageable.getPageSize()) : 0;
                long endRow = offset + pageable.getPageSize();
                stringBuilder.append(" OFFSET ? ROWS FETCH NEXT ? ROWS ONLY ");
                params.add(offset);
                params.add(endRow - offset);
            }
            sql.append(stringBuilder);
        }
        return sql;
    }

}
