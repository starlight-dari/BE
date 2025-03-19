package com.example.startlight.memoryAlbum.service;

import com.example.startlight.memoryAlbum.dao.MemoryAlbumDao;
import com.example.startlight.memoryAlbum.dto.AlbumByPetRepDto;
import com.example.startlight.memoryAlbum.dto.MemoryAlbumRepDto;
import com.example.startlight.memoryAlbum.dto.MemoryAlbumReqDto;
import com.example.startlight.memoryAlbum.dto.MemoryAlbumSimpleDto;
import com.example.startlight.memoryAlbum.entity.MemoryAlbum;
import com.example.startlight.memoryAlbum.repository.MemoryAlbumRepository;
import com.example.startlight.pet.dao.PetDao;
import com.example.startlight.pet.entity.Pet;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class MemoryAlbumService {
    private final MemoryAlbumFlaskService memoryAlbumFlaskService;
    private final MemoryAlbumRepository memoryAlbumRepository;
    private final MemoryAlbumDao memoryAlbumDao;
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

    public List<MemoryAlbumSimpleDto> getMemoryAlbumByPet(Long petId) {
        List<MemoryAlbum> byPetId = memoryAlbumDao.findByPetId(petId);
        return byPetId.stream()
                .map(this::toSimpleDto)
                .collect(Collectors.toList());
    }

    public MemoryAlbumRepDto getMemoryAlbum(Long letterId) {
        MemoryAlbum byId = memoryAlbumDao.findById(letterId);
        return toResponseDto(byId);

    }

    private MemoryAlbumSimpleDto toSimpleDto(MemoryAlbum memoryAlbum) {
        return MemoryAlbumSimpleDto.builder()
                .letter_id(memoryAlbum.getLetter_id())
                .pet_id(memoryAlbum.getPet().getPet_id()) // Pet 엔티티에서 ID 매핑
                .content(memoryAlbum.getContent())
                .createdAt(memoryAlbum.getCreatedAt())
                .opened(memoryAlbum.getOpened())
                .build();
    }

    public MemoryAlbumRepDto createMemoryAlbum(MemoryAlbumReqDto memoryAlbumReqDto) {
        Pet selectedPet = petDao.selectPet(memoryAlbumReqDto.getPet_id());
        MemoryAlbum memoryAlbum = MemoryAlbum.builder()
                .pet(selectedPet)
                .content(memoryAlbumReqDto.getContent())
                .images(memoryAlbumReqDto.getImages())
                .build();
        MemoryAlbum createdAlbum = memoryAlbumDao.createMemoryAlbum(memoryAlbum);
        return toResponseDto(createdAlbum);
    }

    private MemoryAlbumRepDto toResponseDto(MemoryAlbum memoryAlbum) {
        return MemoryAlbumRepDto.builder()
                .letter_id(memoryAlbum.getLetter_id())
                .pet_id(memoryAlbum.getPet().getPet_id())
                .content(memoryAlbum.getContent())
                .images(memoryAlbum.getImages())
                .createdAt(memoryAlbum.getCreatedAt())
                .opened(memoryAlbum.getOpened()).build();
    }
}
