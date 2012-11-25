package web;

import javax.security.auth.Subject;

import org.apache.wicket.ConverterLocator;
import org.apache.wicket.IConverterLocator;
import org.apache.wicket.Page;
import org.apache.wicket.Session;
import org.apache.wicket.protocol.http.WebApplication;
import org.apache.wicket.request.Request;
import org.apache.wicket.request.Response;
import org.apache.wicket.request.resource.PackageResourceReference;
import org.apache.wicket.request.resource.ResourceReference;
import org.apache.wicket.spring.injection.annot.SpringComponentInjector;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import web.common.HibernateRequestCycleListener;

@Component
public class SozialnetzApp extends WebApplication {

	public static final ResourceReference ADD_ICON = new PackageResourceReference(SozialnetzApp.class, "resources/add.png");
	public static final ResourceReference EDIT_ICON = new PackageResourceReference(SozialnetzApp.class, "resources/edit.gif");
	public static final ResourceReference DELETE_ICON = new PackageResourceReference(SozialnetzApp.class, "resources/delete.gif");

	private final SessionFactory sessionFactory;
	
	@Autowired
	public SozialnetzApp(SessionFactory sessionFactory) {
		this.sessionFactory = sessionFactory;
	}
	
	@Override
	public Class<? extends Page> getHomePage() {
		return HomePage.class;
	}
	
	@Override
	protected void init() {
		super.init();
		getComponentInstantiationListeners().add(new SpringComponentInjector(this));
		getRequestCycleListeners().add(new HibernateRequestCycleListener(sessionFactory));
	}

	@Override
	public Session newSession(Request request, Response response) {
		return new SozialneztSession(request);
	}

	
}
