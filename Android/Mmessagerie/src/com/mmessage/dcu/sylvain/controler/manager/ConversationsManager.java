package com.mmessage.dcu.sylvain.controler.manager;

import java.util.LinkedList;
import java.util.List;

import com.mmessage.dcu.sylvain.interfaces.ItemManager;
import com.mmessage.dcu.sylvain.interfaces.Iterator;
import com.mmessage.dcu.sylvain.interfaces.OnTaskCompleted;
import com.mmessage.dcu.sylvain.model.Conversation;
import com.mmessage.dcu.sylvain.tasks.GetRESTTask;

public class ConversationsManager implements ItemManager,
		Iterator<Conversation>, OnTaskCompleted {

	private List<Conversation> _conversations = new LinkedList<Conversation>();
	private Iterator<Conversation> _iterator = null; // TODO chelou d'avoir
														// besoin du cast
	private String _urlPostUser = "http://message.eventhub.eu/conversations";
	private OnTaskCompleted _listener;

	// TODO attention si on fait un refresh data, l'itérator merde

	public ConversationsManager(OnTaskCompleted parListener) {
		_listener = parListener;
	}

	@Override
	public boolean hasNext() {
		return _iterator.hasNext();
	}

	@Override
	public boolean hasPrevious() {
		return _iterator.hasPrevious();
	}

	@Override
	public Conversation next() {
		return _iterator.next();
	}

	@Override
	public Conversation previous() {
		return _iterator.previous();
	}

	@Override
	public void initData() {
		new GetRESTTask(this).execute(_urlPostUser);

	}

	@Override
	public void onTaskCompleted(Object parObject) {
		// TODO créer la list

		_iterator = (Iterator<Conversation>) _conversations.iterator();
		_listener.onTaskCompleted(Boolean.valueOf(true));
	}

}
