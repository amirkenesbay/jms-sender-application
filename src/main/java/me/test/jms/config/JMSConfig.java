package me.test.jms.config;

import com.bea.core.repackaged.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.jms.ConnectionFactory;
import javax.jms.JMSException;
import javax.jms.Queue;
import javax.jms.Session;
import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import java.util.Hashtable;
import java.util.Properties;

@Configuration
public class JMSConfig {
    private Properties props;

    public JMSConfig(@Qualifier("jms_props") Properties props) {
        this.props = props;
    }

    @Bean
    public ConnectionFactory connectionFactory(InitialContext context) throws NamingException {
        return (ConnectionFactory) context.lookup(props.getProperty("jms.factory"));
    }

    @Bean
    public Session session(ConnectionFactory factory) throws JMSException {
        return factory.createConnection().createSession(false, Session.AUTO_ACKNOWLEDGE);
    }

    @Bean
    public Queue queue(InitialContext context) throws NamingException {
        return (Queue) context.lookup(props.getProperty("jms.queue"));
    }

    @Bean
    public InitialContext initialContext() throws NamingException {
        Hashtable<String, String> config = new Hashtable<>();

        config.put(Context.PROVIDER_URL, props.getProperty("wl.url"));
        config.put(Context.SECURITY_PRINCIPAL, props.getProperty("wl.login"));
        config.put(Context.SECURITY_CREDENTIALS, props.getProperty("wl.password"));
        config.put(Context.INITIAL_CONTEXT_FACTORY, "weblogic.jndi.WLInitialContextFactory");

        return new InitialContext(config);
    }
}
