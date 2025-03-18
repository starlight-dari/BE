package com.example.startlight.memoryAlbum.service;

import com.example.startlight.memoryAlbum.dao.MemoryAlbumDao;
import com.example.startlight.memoryAlbum.dto.AlbumByPetRepDto;
import com.example.startlight.memoryAlbum.entity.MemoryAlbum;
import com.example.startlight.memoryAlbum.repository.MemoryAlbumRepository;
import com.example.startlight.pet.dao.PetDao;
import com.example.startlight.pet.entity.Pet;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class MemoryAlbumService {
    private final MemoryAlbumFlaskService memoryAlbumFlaskService;
    private final MemoryAlbumRepository memoryAlbumRepository;
    private final PetDao petDao;

    public List<AlbumByPetRepDto> getMemoryAlbumStatusByPet() {
        Long userId = 3879188713L;
        List<Pet> pets = petDao.selectAllPet(userId);
        List<AlbumByPetRepDto> albumByPetRepDtos = new ArrayList<>();
        for (Pet pet : pets) {
            if(pet.getAlbumStarted()) {
                Integer notOpenedCount = memoryAlbumRepository.countNotOpenedByPetId(pet.getPet_id());
                boolean arrived = false;
                if(notOpenedCount > 0) { arrived = true;}
                AlbumByPetRepDto petRepDto = AlbumByPetRepDto.builder()
                        .petId(pet.getPet_id())
                        .petName(pet.getPet_name())
                        .imgUrl(pet.getPet_img())
                        .albumStarted(pet.getAlbumStarted())
                        .arrived(arrived)
                        .arrivedCount(notOpenedCount)
                        .build();
                albumByPetRepDtos.add(petRepDto);
            }
            else {
                AlbumByPetRepDto petRepDto = AlbumByPetRepDto.builder()
                        .petId(pet.getPet_id())
                        .petName(pet.getPet_name())
                        .imgUrl(pet.getPet_img())
                        .albumStarted(pet.getAlbumStarted())
                        .arrived(false)
                        .arrivedCount(0)
                        .build();
                albumByPetRepDtos.add(petRepDto);
            }
        }
       return albumByPetRepDtos;
    }

    public void createAlbum() {
        memoryAlbumFlaskService.generateMemoryAlbum();
    }
}
