package org.dng.crud_2022_09_28.Servlets;

import java.io.*;
import java.sql.SQLException;
import java.util.List;

import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.*;
import jakarta.servlet.annotation.*;
import org.dng.crud_2022_09_28.DAO.*;
import org.dng.crud_2022_09_28.Model.VinylRecord;

@WebServlet(name = "menuServlet", value = "/")
public class MenuServlet extends HttpServlet {
//    private static final long serialVersionUID = 1L;
//    private final ICRUD dao = new DAO_JDBC();
//    private final ICRUD dao = new DAO_Hibernate();
    private final ICRUD dao = new DAO_Hibernate_NamedQuery();

    protected void doPost(HttpServletRequest request, HttpServletResponse response) {
        doGet(request, response);
    }

    @Override
    public void init() throws ServletException {
        super.init();
        CreateAndFillDB.makeBD();
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) {
        String action = request.getServletPath();
//        System.out.println("action = "+action);
        try {
            switch (action) {
                case "/new" -> showNewForm(request, response);
                case "/insert" -> insert(request, response);
                case "/delete" -> delete(request, response);
                case "/edit" -> showEditForm(request, response);
                case "/update" -> update(request, response);
                case "/search" -> showSearchForm(request, response);
                case "/find" -> find(request, response);
                default -> listItems(request, response);
            }

        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }


    private void listItems(HttpServletRequest request, HttpServletResponse response) {
        List<VinylRecord> listItems = dao.selectAll();
        request.setAttribute("listItems", listItems);
        RequestDispatcher dispatcher = request.getRequestDispatcher("item-list.jsp");
        try {
            dispatcher.forward(request, response);
        } catch (ServletException | IOException e) {
            e.printStackTrace();
        }
    }

    private void showNewForm(HttpServletRequest request, HttpServletResponse response){
        RequestDispatcher dispatcher = request.getRequestDispatcher("item-form.jsp");
        request.setAttribute("mode", "insert");
        try {
            dispatcher.forward(request, response);
        } catch (ServletException | IOException e) {
            e.printStackTrace();
        }
    }

    private void showEditForm(HttpServletRequest request, HttpServletResponse response){
        int id = Integer.parseInt(request.getParameter("id"));
        VinylRecord existingItem = dao.selectById(id);
        RequestDispatcher dispatcher = request.getRequestDispatcher("item-form.jsp");
        request.setAttribute("item", existingItem);
        request.setAttribute("mode", "update");
        try {
            dispatcher.forward(request, response);
        } catch (ServletException | IOException e) {
            e.printStackTrace();
        }
    }

    private void showSearchForm(HttpServletRequest request, HttpServletResponse response){
        RequestDispatcher dispatcher = request.getRequestDispatcher("item-form.jsp");
        request.setAttribute("mode", "search");
        try {
            dispatcher.forward(request, response);
        } catch (ServletException | IOException e) {
            e.printStackTrace();
        }
    }
    private void find(HttpServletRequest request, HttpServletResponse response){
        //int id = Integer.parseInt(request.getParameter("id"));
        String name = request.getParameter("name");
        String author = request.getParameter("author");

        int year = 0;
        if (request.getParameter("year") != "") {
            year = Integer.parseInt(request.getParameter("year"));
        }

        List<VinylRecord> listItems = dao.searchByParam(name.trim(), author.trim(), year);
        request.setAttribute("listItems", listItems);
        RequestDispatcher dispatcher = request.getRequestDispatcher("item-list.jsp");
        try {
            dispatcher.forward(request, response);
        } catch (ServletException | IOException e) {
            e.printStackTrace();
        }
    }


    private void insert(HttpServletRequest request, HttpServletResponse response){
        String name = request.getParameter("name");
        String author = request.getParameter("author");
        int year = Integer.parseInt(request.getParameter("year"));
        VinylRecord newItem = new VinylRecord( name, author, year);
        dao.insert(newItem);
        try {
            response.sendRedirect("list");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void update(HttpServletRequest request, HttpServletResponse response){
        int id = Integer.parseInt(request.getParameter("id"));
        String name = request.getParameter("name");
        String author = request.getParameter("author");
        int year = Integer.parseInt(request.getParameter("year"));

        VinylRecord item = new VinylRecord(id, name, author, year);
        dao.update(item);
        try {
            response.sendRedirect("list");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void delete(HttpServletRequest request, HttpServletResponse response){
        long id = Long.parseLong(request.getParameter("id"));
        dao.deleteById(id);
        try {
            response.sendRedirect("list");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void printSQLException(SQLException ex) {
        for (Throwable e: ex) {
            if (e instanceof SQLException) {
                e.printStackTrace(System.err);
                System.err.println("SQLState: " + ((SQLException) e).getSQLState());
                System.err.println("Error Code: " + ((SQLException) e).getErrorCode());
                System.err.println("Message: " + e.getMessage());
                Throwable t = ex.getCause();
                while (t != null) {
                    System.out.println("Cause: " + t);
                    t = t.getCause();
                }
            }
        }
    }
}