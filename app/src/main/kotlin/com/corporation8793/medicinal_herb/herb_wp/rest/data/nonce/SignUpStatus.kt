package com.corporation8793.medicinal_herb.herb_wp.rest.data.nonce

/**
 * SignUpStatus data class
 * @author  두동근
 * @param   status      상태 코드 (expected : "ok")
 * @param   username    회원가입 요청한 회원의 별명
 * @param   error       에러 내용
 */
data class SignUpStatus(val status : String = "", val username : String = "", val error : String = "")