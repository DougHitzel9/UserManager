package com.dhitzel.server.repository;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.dbutils.ResultSetHandler;
import org.springframework.stereotype.Repository;

import com.dhitzel.server.entity.Role;

@Repository
public class RoleRepository extends AbstractRepository {

    /**
     * Does not supress warnings. Cast each element of Collection to Object type.
     * 
     * @return
     * @throws Exception
     */
    public List<Role> findAll() throws Exception {
        List<Role> roleList = new ArrayList<>();

        String query = "SELECT r.role_id, r.role FROM role r";

        RoleListResultSetHandler resultSetHandler = new RoleListResultSetHandler();

        List<?> genericList = listQuery(query, resultSetHandler);

        for (int i=0; i<genericList.size(); i++) {
            Role role = (Role) genericList.get(i);
            roleList.add(role);
        }
        return roleList;
    }  

    public Role findById(long id) throws Exception {

        String query = "SELECT r.role_id, r.role FROM role r WHERE role_id = ?";

        Object[] params = new Object[1];
        params[0] = id;

        RoleResultSetHandler resultSetHandler = new RoleResultSetHandler();

        return (Role) query(query, resultSetHandler, params);
    }

    public void reset() throws Exception {
        String call = "CALL reset()";

        Object[] params = new Object[0];

        execute(call, params);
    }

    /**
     * RoleResultSetHandler
     */
    protected class RoleResultSetHandler implements ResultSetHandler<Role> {
        public Role handle(ResultSet rs) {

            Role role = null;

            try {
                if (rs.next()) {
                    long roleId = rs.getLong("role_id");

                    String title = rs.getString("role");

                    role = new Role(roleId, title);
                }
            }
            catch (SQLException sqle) {
                logger.error("Unable to process ResultSet. Exception encountered - SQLException encountered - " + sqle);
            }

            return role;
        }
    }

    /**
     * RoleListResultSetHandler
     */
    protected class RoleListResultSetHandler implements ResultSetHandler<List<Role>> {
        public List<Role> handle(ResultSet rs) {

            List<Role> roleList = new ArrayList<Role>();

            try {
                while (rs.next()) {
                    long roleId = rs.getLong("role_id");

                    String title = rs.getString("role");

                    Role role = new Role(roleId, title);

                    roleList.add(role);
                }
            }
            catch (SQLException sqle) {
                logger.error("Unable to process ResultSet. Exception encountered - SQLException encountered - " + sqle);
            }

            return roleList;
        }
    }
}