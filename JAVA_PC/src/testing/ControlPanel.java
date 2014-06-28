package testing;

import java.awt.BorderLayout;
import java.awt.Checkbox;
import java.awt.CheckboxGroup;
import java.awt.Dimension;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import javax.swing.JPanel;
import engine.logic.model.User;
import Utils.Logg;

@SuppressWarnings("serial")
public class ControlPanel extends JPanel implements ItemListener
{
	private CheckboxGroup userGroupRb;
	private Checkbox rbUsers[];
	private User activeUser;
	private User users[];
	
	public ControlPanel(User users[]) 
	{
		this.users = users;
		this.rbUsers = createUserRbGroup(new CheckboxGroup(), users);
		this.activeUser = users[0];
		addCheckBoxGroupToFrame(this.rbUsers);
		setPreferredSize(new Dimension(200, 200));		
	}
	
	private void addCheckBoxGroupToFrame(Checkbox rbUsers[])
	{
		for(int i = 0; i < rbUsers.length;  i++)
		{
			rbUsers[i].addItemListener(this);
			add(rbUsers[i], BorderLayout.EAST);			
		}
	}
	
	private Checkbox[] createUserRbGroup(CheckboxGroup ckBoxGroup, User users[])
	{
		assert(users != null);
		
		Checkbox rbUsers[] = new Checkbox[users.length];
		for(int i = 0; i < users.length;  i++)
		{
			rbUsers[i] = new Checkbox(users[i].getName(), ckBoxGroup, (i==0)?true:false);			
		}

		return rbUsers;
	}
	
	public User getActiveUser()
	{
		return this.activeUser;
	}
	
	@Override
	public void itemStateChanged(ItemEvent e)
	{		
		for(int i = 0; i < rbUsers.length; i++)
		{
			if (e.getSource() == this.rbUsers[i])
			{
				this.activeUser = users[i];
				return;
			}
		}
		if(e.getSource() == this.rbUsers[0])
		{
			Logg.INFO("User Selected : " + this.rbUsers[0].toString());
		}
		else if (e.getSource() == this.rbUsers[1])
		{
			Logg.INFO("User Selected : " + this.rbUsers[1].toString());
		} 
	}

}
