package solutions.alva.of.son.tato.classes

import android.net.Uri
import androidx.annotation.Keep

@NoArg
data class Users(
    var uId: String? = null,
    var userName: String? = null,
    var userType: String? = null,
    var userNum: String? = null,
    var userDep: String? = null,
    var techProf: String? = null,
    var callPref : String? = null,
    var imageId : Uri? = null,
    var techAmountReviews : Int? = null,
    var techRateFinal: Float? = null
    )


annotation class NoArg
