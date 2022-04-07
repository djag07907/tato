package solutions.alva.of.son.tato.classes

import androidx.annotation.Keep

@NoArg
data class Users(
    var uId: String? = null,
    var userName: String? = null,
    var userType: String? = null,
    var userNum: String? = null,
    var userDep: String? = null,
    var techProf: String? = null,
    var imageId : Int? = null
    )


annotation class NoArg
