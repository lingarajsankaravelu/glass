package lingaraj.hourglass.in.glass.injection;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import javax.inject.Scope;

@Scope
@Retention(RetentionPolicy.RUNTIME)
public @interface ActivityScope {
  //provides runtime scope for any variable inside module. because you cannot combain scoped varaiable with unscoped
  // i.e @singleton cannot be combined with other things.
}
