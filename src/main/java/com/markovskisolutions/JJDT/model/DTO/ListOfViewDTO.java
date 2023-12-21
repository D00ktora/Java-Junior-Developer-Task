package com.markovskisolutions.JJDT.model.DTO;

import java.util.List;

public class ListOfViewDTO {
    private List<ViewDTO> viewDTO;

    public List<ViewDTO> getViewDTO() {
        return viewDTO;
    }

    public ListOfViewDTO setViewDTO(List<ViewDTO> vIewDTO) {
        this.viewDTO = vIewDTO;
        return this;
    }
}
