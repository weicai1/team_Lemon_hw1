package hello;

        import javax.persistence.Entity;
        import javax.persistence.GeneratedValue;
        import javax.persistence.GenerationType;
        import javax.persistence.Id;

@Entity // This tells Hibernate to make a table out of this class
public class pastgame {
    @Id
    @GeneratedValue(strategy= GenerationType.AUTO)
    private Integer id;

    private String time;

    private String aiword;

    private String userword;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getAiword() {
        return aiword;
    }

    public void setAiword(String aiword) {
        this.aiword = aiword;
    }

    public String getUserword() {
        return userword;
    }

    public void setUserword(String userword) {
        this.userword = userword;
    }

}