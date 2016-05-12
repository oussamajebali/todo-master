package com.training.todo_list.model.managers;

import com.training.todo_list.model.models.Todo;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.UUID;

/**
 * This is just a very simple implementation of a manager allowing to create, save and retrieve
 * a list of Todos. Nothing is saved into a database or file, it is initialized
 * in the method onCreate of TodoListApplication.
 * Just create a `new TodoManager()`, and you will be able to :
 * - retrieve all Todos,
 * - retrive a Todos by Id
 * - create a new Todo
 * The save method is not really useful.
 */

public class TodoManager {

    private static final ArrayList<Todo> mTodos = new ArrayList<>();


    public List<Todo> all() {
        return new ArrayList<>(mTodos);
    }


    public Todo todoFor(UUID pIdTodo) {
        for (Todo tTodo : mTodos)
            if (tTodo.id().equals(pIdTodo))
                return tTodo;
        return null;
    }


    public void save(Todo pTodo) {
        if (null != pTodo && null != pTodo.id())
            mTodos.add(pTodo);
    }

    public void update(int pIindex, Todo pTodo) {
        if (null != pTodo && null != pTodo.id())
            mTodos.set(pIindex,pTodo);
    }


    public Todo create(String pSDescription, Date pDateCreation, UUID pIdTodoType, boolean pBIsDone) {
        UUID tIdNew = UUID.randomUUID();
        Todo tTodo = new Todo(pSDescription, pDateCreation, pIdTodoType, pBIsDone, tIdNew);
        mTodos.add(tTodo);
        return tTodo;
    }
}
