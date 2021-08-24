import java.lang.RuntimeException

fun main() {
    /*var array = emptyArray<Attachment>()
    array += PhotoAttachment("photo", Photo(1, 1, 1, 1, "фото 1", 800,
        null, 800, 600))
    array += EventAttachment("event" , Event(1, 3600, 1, true, "какой-то адрес",
        "Встреча","Встреча",null))
    val service = WallService()
    val post = service.add(Post(1,10,11,200,300,"First post",4,5,
        false, Comments(3,true,true,true,true),
        Copyright(1,"link1","name1","type1"), Likes(321,true,true,true),
        Reposts(8,true),Views(220),"post",300,true,true,
        false,false,false,true,Donut(true,500,
            "Ivanov",true,"edit"),8, null, null, null, array))*/

}

class WallService {
    private var posts = emptyArray<Post>()
    private var id = 1

    private var comments = emptyArray<Comment>()

    fun createComment(comment: Comment) {
        for ((index, postInArray) in posts.withIndex()) {
            if (comment.postId == postInArray.id) {
                comments += comment
                return
            }
        }
        // исключение
        throw PostNotFoundException("no post with id ${comment.id}")
    }

    fun add(post: Post): Post {
        post.id = id
        id++
        posts += post
        return posts.last()
    }

    fun update(post: Post): Boolean {
        for ((index, postInArray) in posts.withIndex()) {
            if (post.id == postInArray.id) {
                posts[index] = post.copy(ownerId = postInArray.ownerId, date = postInArray.date)
                return true
            }
        }
        return false
    }
}

class PostNotFoundException(message: String): RuntimeException(message)

data class Post(
    var id: Int,
    val ownerId: Int,
    val fromId: Int,
    val createdBy: Int,
    val date: Int,
    val text: String,
    val replyOwnerId: Int,
    val replyPostId: Int,
    val friendsOnly: Boolean,
    val comments: Comments,
    val copyright: Copyright?,
    val likes: Likes,
    val reposts: Reposts,
    val views: Views,
    val postType: String,
    val signerId: Int,
    val canPin: Boolean,
    val canDelete: Boolean,
    val canEdit: Boolean,
    val isPinned: Boolean,
    val markedAsAds: Boolean,
    val isFavorite: Boolean,
    val donut: Donut,
    val postponedId: Int,
    val postSource: PostSource?,
    val geo: Geo?,
    val copyHistory: Array<Post>?,
    val attachments: Array<Attachment>?
)

data class Comments(
    val count: Int,
    val canPost: Boolean,
    val groupsCanPost: Boolean,
    val canClose: Boolean,
    val canOpen: Boolean
)

data class Copyright(
    val id: Int,
    val link: String,
    val name: String,
    val type: String
)

data class Likes(
    val count: Int,
    val userLikes: Boolean,
    val canLike: Boolean,
    val canPublish: Boolean
)

data class Reposts(
    val count: Int,
    val userReposted: Boolean
)

data class Views(
    val count: Int
)

data class Donut(
    val isDonut: Boolean,
    val paidDuration: Int,
    val placeholder: String,
    val canPublishFreeCopy: Boolean,
    val editMode: String
)

data class PostSource(
    val type: String,
    val platform: String,
    val data: String,
    val url: String
)

data class Place(
    val id: Int,
    val title: String,
    val latitude: Int,
    val longitude: Int,
    val created: Int,
    val icon: String,
    val chekins: Int,
    val updated: Int,
    val type: Int,
    val country: Int,
    val city: Int,
    val address: String
)

data class Geo(
    val type: String,
    val coordinates: String,
    val place: Place?
)

interface Attachment {
    val type: String
}

data class PhotoAttachment(override val type: String = "photo", val photo: Photo) : Attachment

data class Photo(
    val id: Int,
    val albumId: Int,
    val ownerId: Int,
    val userId: Int,
    val text: String,
    val date: Int,
    val sizes: Array<SizesPhoto>?,
    val width: Int,
    val height: Int
)

data class SizesPhoto(
    val type: String,
    val url: String,
    val width: Int,
    val height: Int
)

data class PostedPhotoAttachment(override val type: String = "posted_photo", val postedPhoto: PostedPhoto) : Attachment

data class PostedPhoto(
    val id: Int,
    val ownerId: Int,
    val photo130: String,
    val photo604: String
)

data class AudioAttachment(override val type: String = "audio", val audio: Audio) : Attachment

data class Audio(
    val id: Int,
    val ownerId: Int,
    val artist: String,
    val title: String,
    val duration: Int,
    val url: String,
    val lyricsId: Int,
    val albumId: Int,
    val genreId: Int,
    val date: Int,
    val noSearch: Boolean,
    val isHd: Boolean
)

data class PageAttachment(override val type: String = "page", val page: Page) : Attachment

data class Page(
    val id: Int,
    val groupId: Int,
    val creatorId: Int,
    val title: String,
    val currentUserCanEdit: Boolean,
    val currentUserCanEditAccess: Boolean,
    val whoCanView: Int,
    val whoCanEdit: Int,
    val edited: Int,
    val created: Int,
    val editorId: Int,
    val views: Int,
    val parent: String,
    val parent2: String,
    val source: String,
    val html: String,
    val viewUrl: String
)

data class EventAttachment(override val type: String = "event", val event: Event) : Attachment

data class Event(
    val id: Int,
    val time: Int,
    val memberStatus: Int,
    val isFavorite: Boolean,
    val address: String,
    val text: String,
    val buttonText: String,
    val friens: Array<Int>?
)

data class Comment(
    val postId: Int,
    val id: Int,
    val fromId: Int,
    val date: Int,
    val text: String,
    val donut: Donut,
    val replyToUser: Int,
    val replyToComment: Int,
    val attachments: Array<Attachment>?,
    val parentsStack: Array<Comment>?,
    val thread: ThreadComments?
)

data class ThreadComments(
    val count: Int,
    val items: Array<Comment>,
    val canPost: Boolean,
    val showReplyButton: Boolean,
    val groupsCanPost: Boolean
)
