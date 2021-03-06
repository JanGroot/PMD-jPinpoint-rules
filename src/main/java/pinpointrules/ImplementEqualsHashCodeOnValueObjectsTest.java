package pinpointrules;

import lombok.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Controller;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import javax.ejb.Singleton;
import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.inject.Produces;
import javax.enterprise.inject.spi.InjectionPoint;
import javax.inject.Inject;
import javax.persistence.Entity;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Properties;


/**
 * Created by BorgersJM on 5-8-2016.
 * Value object assumed:
 * * has at least 1 instance field and
 * * (no equals or no hashCode) and (
 * * has toString() method or
 * * classname ends with 'Dto' or
 * * has N fields and exactly N getters)
 */
public class ImplementEqualsHashCodeOnValueObjectsTest {
    private String someState = "some";

    @Override
    public String toString() {
        return "ImplementEqualsHashCodeOnValueObjectsTest{" +
                "someState='" + someState + '\'' +
                '}';
    }
    // state and toString and no equals/hashCode --> violation
}

class Ok1 {
    private String someState = "some";

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Ok1 ok1 = (Ok1) o;

        return someState != null ? someState.equals(ok1.someState) : ok1.someState == null;

    }

    @Override
    public int hashCode() {
        return someState != null ? someState.hashCode() : 0;
    }

    @Override
    public String toString() {
        return "Ok1{}";
    }
}

class Ok2 {
    private String someState = "some";
    // no toString, no getter, so may not be a value object
}

class Ok3 {
    private static final String noInstanceState = "class-level";
    // no state

    @Override
    public String toString() {
        return "Ok3{}";
    }

}

abstract class Ok4 { // abstract, so no violation
    private String someState = "some";

    @Override
    public String toString() {
        return "Ok4{}";
    }

}

class ViolateDto {
    private String someState = "some";

    public String getSomeState() {
        return someState;
    }
}

class OkDto {
    private String someState = "some";

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        OkDto okDto = (OkDto) o;

        return someState != null ? someState.equals(okDto.someState) : okDto.someState == null;

    }

    @Override
    public int hashCode() {
        return someState != null ? someState.hashCode() : 0;
    }
}

class ViolateGetters {
    private String someState1 = "some1";
    private String someState2 = "some2";


    public String getSomeState2() {
        return someState2;
    }

    public String getSomeState1() {
        return someState1;
    }
}

class ViolateGetters2 {
    private String someState1 = "some1";
    private boolean someState2 = true;


    public boolean isSomeState2() {
        return someState2;
    }

    public String getSomeState1() {
        return someState1;
    }
}

class OkGetters {
    private final String someState1 = "some1";
    private String someState2 = "some2";


    public String getSomeState2() {
        return someState2;
    }

    public String getSomeState1() {
        return someState1;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        OkGetters okGetters = (OkGetters) o;

        if (!someState1.equals(okGetters.someState1)) return false;
        return someState2.equals(okGetters.someState2);
    }

    @Override
    public int hashCode() {
        int result = someState1.hashCode();
        result = 31 * result + someState2.hashCode();
        return result;
    }
}

class OkGetters2 {
    private final String someState1 = "some1";
    private boolean someState2 = true;


    public boolean getSomeState2() {
        return someState2;
    }

    public String getSomeState1() {
        return someState1;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        OkGetters2 okGetters = (OkGetters2) o;

        if (!someState1.equals(okGetters.someState1)) return false;
        return (someState2 == someState2);
    }

    @Override
    public int hashCode() {
        int result = someState1.hashCode();
        result = 31 * result + ((someState2 == true) ? 1 : 0);
        return result;
    }

}

class OkGet {
    private final String someState1 = "some1";
    private boolean someState2 = true;


    public OkGet get() {
        return this;
    } // get is not counted as getter

    public String getSomeState1() {
        return someState1;
    }

}

class OkMuchMoreFieldsThanGetters {
    private final String someState1 = "some1";
    private final String someState2 = "some2";
    private boolean someState3 = true;

    public String getSomeState1() {
        return someState1;
    }

    @Override
    public String toString() {
        return "OkMuchMoreFieldsThanGetters{}";
    }
}

class OkException extends RuntimeException {
    private String someState1 = "some1";
    private boolean someState2 = true;

    public OkException(String message, Throwable cause) {
        super(message, cause);
    }

    public boolean isSomeState2() {
        return someState2;
    }

    public String getSomeState1() {
        return someState1;
    }
}

@Data
class OkLombok {
    private String someState1 = "some1";

    public String getSomeState1() {
        return someState1;
    }
}

@EqualsAndHashCode
class OkLombok2 {
    private String someState1 = "some1";
    private boolean someState2 = true;

    public boolean isSomeState2() {
        return someState2;
    }

    public String getSomeState1() {
        return someState1;
    }
}

@Value
class OkLombok3 {
    private String someState1 = "some1";

    public String getSomeState1() {
        return someState1;
    }
}

@Singleton
class OkEjb {
    private String someState1 = "some1";

    public String getSomeState1() {
        return someState1;
    }
}

@Component
class OkSpring1 {
    private final String someState1 = "some1";

    public String getSomeState1() {
        return someState1;
    }
}

@Service
class OkSpring2 {
    private final String someState1 = "some1";

    public String getSomeState1() {
        return someState1;
    }
}

@ApplicationScoped
class OkLoggerProducer {

    @Inject
    //@PropertyFile(envEntry = "loggerPropertyFiles")
    private Properties properties;

    @PostConstruct
    private void init() {
        System.out.println(this.getClass().getCanonicalName() + " - Initializing, Properties file contains: " + properties);
        //PropertyConfigurator.configure(properties);
    }

    @Produces
    public Logger getLogger(InjectionPoint injectionPoint) {
        return LoggerFactory.getLogger(injectionPoint.getMember().getDeclaringClass());
    }

}

@ApplicationScoped
class ViolateLoggerProducer
{

    @Inject
    //@PropertyFile(envEntry = "loggerPropertyFiles")
    private Properties properties;

    @PostConstruct
    private void init() {
        System.out.println(this.getClass().getCanonicalName() + " - Initializing, Properties file contains: " + properties);
        //PropertyConfigurator.configure(properties);
    }

    public Properties getProperties() {return properties;}
}

@EqualsAndHashCode(callSuper = false, doNotUseGetters = true, exclude = {"peanutVerzoek"})
@XmlAccessorType(XmlAccessType.NONE)
final class EigenToggleActiviteitOk {
    private Object peanutVerzoek;
    private String test;

    public String getName() {
        return "Eigentoggle";
    }
    public Object getCake() {
        return peanutVerzoek;
    }
}

// static field doesn't make it a value object
class SprinklerSettingsControllerOK {

    private final static List<String> JELLY_AND_RBB_SERVICE_CONCEPTS = Arrays.asList("RM", "BF", "BS");
    private final Object sprinklerSettingsBO;
    //private final String str = "blah";

    public SprinklerSettingsControllerOK(Object sprinklerSettingsBO) {
        this.sprinklerSettingsBO = sprinklerSettingsBO;
    }

    public List<String> getSprinklersList() throws Exception {
        return Collections.<String>emptyList();
    }

    public boolean isDesiredDateInThePastOrNull() {
        return true;
    }

}

// When @Controller and has value
@Controller
class SprinklerSettingsControllerViolation {

    private final static List<String> JELLY_AND_RBB_SERVICE_CONCEPTS = Arrays.asList("RM", "BF", "BS");
    private final Object sprinklerSettingsBO;
    //private final String str = "blah";

    public SprinklerSettingsControllerViolation(Object sprinklerSettingsBO) {
        this.sprinklerSettingsBO = sprinklerSettingsBO;
    }

    public List<String> getSprinklersList() throws Exception {
        return Collections.<String>emptyList();
    }

    public boolean isDesiredDateInThePastOrNull() {
        return true;
    }

}

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "RequestError", propOrder = {
        "requestErrorCode",
        "requestErrorDetails"
})
class RequestErrorOk {

    @XmlElement(name = "RequestErrorCode", required = true)
    protected String requestErrorCode;
    @XmlElement(name = "RequestErrorDetails")
    protected String requestErrorDetails;

    public String getRequestErrorCode() {
        return requestErrorCode;
    }

    public void setRequestErrorCode(String value) {
        this.requestErrorCode = value;
    }

    public String getRequestErrorDetails() {
        return requestErrorDetails;
    }

    public void setRequestErrorDetails(String value) {
        this.requestErrorDetails = value;
    }

}

@XmlType
class XmlTypeIgnored {
    private String someState1 = "some1";
    private String someState2 = "some2";

    public String getSomeState2() {
        return someState2;
    }

    public String getSomeState1() {
        return someState1;
    }
}

@Entity
class EntityIgnored {
    private String someState1 = "some1";
    private String someState2 = "some2";

    public String getSomeState2() {
        return someState2;
    }

    public String getSomeState1() {
        return someState1;
    }
}

class AdaptedValidationErrorIgnored {

    @XmlElement
    private final String code = null;

    @XmlElement
    private final String text = null;

    @XmlElement
    private final String field = null;

    public String getCode() {
        return code;
    }

    public String getText() {
        return text;
    }

    public String getField() {
        return field;
    }

}



