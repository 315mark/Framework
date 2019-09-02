package zkch.com.framework.bean;

import java.util.List;

/**
 * Created by zhouwei on 16/11/9.
 */
public class MovieSubject {
   public int count;
   public int start;
   public int total;
   public List<Subject> subjects;
   public String title;

   public int getCount() {
      return count;
   }

   public void setCount(int count) {
      this.count = count;
   }

   public int getStart() {
      return start;
   }

   public void setStart(int start) {
      this.start = start;
   }

   public int getTotal() {
      return total;
   }

   public void setTotal(int total) {
      this.total = total;
   }

   public List<Subject> getSubjects() {
      return subjects;
   }

   public void setSubjects(List<Subject> subjects) {
      this.subjects = subjects;
   }

   public String getTitle() {
      return title;
   }

   public void setTitle(String title) {
      this.title = title;
   }
}
