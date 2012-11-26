package sozialnetz.web.user;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;

import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.panel.Panel;
import org.apache.wicket.markup.repeater.Item;
import org.apache.wicket.markup.repeater.RefreshingView;
import org.apache.wicket.model.IModel;

import sozialnetz.domain.entities.User;
import sozialnetz.web.EntityModel;

@SuppressWarnings("serial")
public class FriendListPanel extends Panel{


	public FriendListPanel(String id, final IModel<Collection<User>> colModel) {
        super(id);
        add(new RefreshingView<User>("friends"){
                @Override
                protected Iterator<IModel<User>> getItemModels(){
                        List<IModel<User>> result = new ArrayList<IModel<User>>();
                        for( User u : colModel.getObject()){
                                result.add(new EntityModel<User>(User.class, u));
                        }
                        return result.iterator();
                }

                @Override
                protected void populateItem(Item<User> item) {
                        item.add(new ProfileLinkPanel("friend", item.getModelObject()));
                }
        });
        add(new Label("empty", "No tiene amigos").setVisible(colModel.getObject().size()==0));
}

}
