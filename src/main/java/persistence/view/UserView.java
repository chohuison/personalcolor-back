package persistence.view;

import persistence.dto.UserDTO;

import java.util.List;

public class UserView {
    public void allView(List<UserDTO> dtos){
        System.out.println(dtos.size());
        System.out.println(dtos.get(0).getID());
    }
}
