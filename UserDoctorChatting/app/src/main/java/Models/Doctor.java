package Models;

public class Doctor {

    String uid;
    String name , email;
    public Doctor(){};


    public void setUid(String uid)
    {
        this.uid = uid;
    }
    public String getUid()
    {
        return uid;
    }

    public void setName(String name)
    {
        this.name = name;
    }
    public String getName()
    {
        return name;
    }

    public void setEmail(String email)
    {
        this.email = email;
    }

    public String getEmail()
    {
        return email;
    }

    @Override
    public String toString()
    {
        return "User(" + "uid='"+ uid + '\'' +
                ", name='" + name + '\'' + ", email='" + email + '\'' +
                '}';
    }

}
