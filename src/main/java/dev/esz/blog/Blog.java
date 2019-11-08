package dev.esz.blog;


import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;


@Entity(name = "blog")
public class Blog {

    @Id
    @GeneratedValue
    private Long id;
    private String title;
    private String story;
    private String writer;

    public Blog() {

    }

    public Blog(Long id, String title, String story, String writer) {
        this.id = id;
        this.title = title;
        this.story = story;
        this.writer = writer;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getStory() {
        return story;
    }

    public void setContent(String story) {
        this.story = story;
    }

    public String getWriter() {
        return writer;
    }

    public void setWriter(String writer) {
        this.writer = writer;
    }

//    @Override
//    public boolean equals(Object o) {
//        if (this == o) return true;
//        if (o == null || getClass() != o.getClass()) return false;
//
//        Blog blog = (Blog) o;
//
//        if (id != null ? !id.equals(blog.id) : blog.id != null) return false;
//        if (title != null ? !title.equals(blog.title) : blog.title != null) return false;
//        if (content != null ? !content.equals(blog.content) : blog.content != null) return false;
//        return writer != null ? writer.equals(blog.writer) : blog.writer == null;
//    }

    @Override
    public int hashCode() {
        int result = id != null ? id.hashCode() : 0;
        result = 31 * result + (title != null ? title.hashCode() : 0);
        result = 31 * result + (story != null ? story.hashCode() : 0);
        result = 31 * result + (writer != null ? writer.hashCode() : 0);
        return result;
    }
}
