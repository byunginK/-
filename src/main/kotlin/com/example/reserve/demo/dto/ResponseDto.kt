package com.example.reserve.demo.dto

import com.example.reserve.demo.enums.ResultStatus
import com.fasterxml.jackson.annotation.JsonInclude

@JsonInclude(JsonInclude.Include.NON_EMPTY)
data class ResponseDto<T>(
    var result: ResultStatus, var resultMessage: String?, var code: Int,
    var errorMessage: String?, var data: Any?, var dataList: List<T>?,
    var itemTotalCount: Long?, var pageTotalCount: Int?
) {

    constructor(result: ResultStatus, resultMessage: String?, code: Int, errorMessage: String?) :
            this(result, resultMessage, code, errorMessage, null, null, null, null) {
    }

    constructor(
        result: ResultStatus,
        resultMessage: String?,
        code: Int,
        itemTotalCount: Long?,
        pageTotalCount: Int?,
        dataList: List<T>?,
    ) : this(result, resultMessage, code, null, null, dataList, itemTotalCount, pageTotalCount)

    constructor(
        result: ResultStatus,
        resultMessage: String?,
        code: Int,
        data: Any?
    ) : this(result, resultMessage, code, null, data,null, null, null)
}