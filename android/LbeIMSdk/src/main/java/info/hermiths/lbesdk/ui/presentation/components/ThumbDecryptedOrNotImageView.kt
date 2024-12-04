package info.hermiths.lbesdk.ui.presentation.components

import android.net.Uri
import android.provider.MediaStore
import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import coil3.ImageLoader
import coil3.compose.AsyncImage
import coil3.compose.LocalPlatformContext
import coil3.request.ImageRequest
import com.google.gson.Gson
import info.hermiths.lbesdk.model.MessageEntity
import info.hermiths.lbesdk.model.resp.MediaSource
import info.hermiths.lbesdk.ui.presentation.viewmodel.ChatScreenViewModel
import info.hermiths.lbesdk.ui.presentation.viewmodel.ChatScreenViewModel.Companion.CONTINUE_UPLOAD
import info.hermiths.mylibrary.R
import java.io.File


@Composable
fun ThumbDecryptedOrNotImageView(
    navController: NavController,
    message: MessageEntity,
    viewModel: ChatScreenViewModel?,
    imageLoader: ImageLoader
) {
    var thumbUrl = ""
    var thumbKey = ""
    var fullUrl = ""
    var fullKey = ""
    try {
        val media = Gson().fromJson(message.msgBody, MediaSource::class.java)
        fullUrl = media.resource.url
        fullKey = media.resource.key
        thumbUrl = media.thumbnail.url
        thumbKey = media.thumbnail.key
    } catch (e: Exception) {
        println("DecryptedOrNotImageView Json parse error -->> ${message.msgBody}")
    }

    val progress = ChatScreenViewModel.progressList[message.clientMsgID]?.collectAsState()
    val thumbBmp = ChatScreenViewModel.uploadThumbs[message.clientMsgID]?.collectAsState()
    Log.d(ChatScreenViewModel.UPLOAD, "thumbBmp isExist: ${thumbBmp == null}, thumbUrl: $thumbUrl")
//    val isGif = FileUtils.isGif(message.localFile?.mimeType ?: "")
    val isGif = false


    Box(contentAlignment = Alignment.Center) {
        val ctx = LocalPlatformContext.current
        val modifier = Modifier
            .fillMaxWidth(0.5f)
            .height(200.dp)
            .clip(RoundedCornerShape(16.dp))
            .clickable {
                if (fullUrl.isNotEmpty()) {
                    navController.navigate("${NavRoute.MEDIA_VIEWER}/${message.clientMsgID}")
                } else {
                    if (message.localFile?.isBigFile == true) {
                        if (!message.pendingUpload) {
                            Log.d(
                                CONTINUE_UPLOAD,
                                "断点暂停, 缓存的进度： ${message.uploadTask?.progress}"
                            )
                            viewModel?.cancelJob(message.clientMsgID, progress = progress)
                        } else {
                            Log.d(CONTINUE_UPLOAD, "续传 ---->>>> ${message.uploadTask}")
                            Log.d(CONTINUE_UPLOAD, "续传 uri ---->>>> ${message.localFile?.path}")
                            Log.d(
                                CONTINUE_UPLOAD,
                                "续传 executeIndex: ${message.uploadTask?.executeIndex}"
                            )
                            val uri = Uri.parse(message.localFile?.path)
                            val cr = ctx.contentResolver
                            val projection = arrayOf(MediaStore.MediaColumns.DATA)
                            val metaCursor = cr.query(uri, projection, null, null, null)
                            metaCursor?.use { mCursor ->
                                if (mCursor.moveToFirst()) {
                                    val path = mCursor.getString(0)
                                    Log.d(
                                        ChatScreenViewModel.UPLOAD, "续传 ---->>>> path: $path"
                                    )
                                    val file = File(path)
                                    viewModel?.continueSplitTrunksUpload(message, file)
                                }
                            }
                        }
                    }
                }
            }

        if (thumbBmp != null) {
            Image(
                bitmap = thumbBmp.value.asImageBitmap(),
                contentDescription = "Yo",
                contentScale = ContentScale.FillWidth,
                modifier = modifier,
            )
        } else {
            AsyncImage(
                model = ImageRequest.Builder(LocalPlatformContext.current)
                    .data(if (isGif) fullUrl else thumbUrl).decoderFactory(
                        DecryptedDecoder.Factory(
                            url = if (isGif) fullUrl else thumbUrl,
                            key = if (isGif) fullKey else thumbKey
                        )
                    ).build(),
                contentDescription = "Yo",
                contentScale = ContentScale.FillWidth,
                modifier = modifier,
                imageLoader = imageLoader,
            )
        }

        if (fullUrl.isNotEmpty()) {
            if (message.msgType == 3) {
                Image(
                    painterResource(R.drawable.play),
                    contentDescription = "",
                    modifier = Modifier.size(32.dp)
                )
            }
        } else {
            if (progress != null) {
                Log.d(
                    ChatScreenViewModel.UPLOAD,
                    "DecryptedOrNotImageView progress --->>> ${progress.value}, clientMsgID -->>> ${message.clientMsgID}, msg progress --->> ${message.uploadTask?.progress}"
                )
                if (progress.value != 1.0f) {
                    Box(contentAlignment = Alignment.Center) {
                        CircularProgressIndicator(
                            progress = {
                                progress.value
                            },
                            color = Color.White,
                            trackColor = Color.White.copy(alpha = 0.3f),
                            strokeWidth = 2.dp,
                        )

                        if (message.pendingUpload) {
                            Image(
                                painterResource(R.drawable.pending),
                                "",
                                modifier = Modifier.size(width = 8.dp, height = 13.dp)
                            )
                        } else {
                            val percent = "${progress.value * 100}"
                            Text(
                                "${
                                    percent.substring(
                                        0, if (percent.length > 5) 5 else percent.length
                                    )
                                }%", style = TextStyle(
                                    fontSize = 8.sp,
                                    fontWeight = FontWeight.W600,
                                    color = Color.White
                                )
                            )
                        }
                    }
                }
                if (progress.value == 1.0f) {
                    if (message.msgType == 3) {
                        Image(
                            painterResource(R.drawable.play),
                            contentDescription = "",
                            modifier = Modifier.size(32.dp)
                        )
                    }
                }
            }
        }
    }
}