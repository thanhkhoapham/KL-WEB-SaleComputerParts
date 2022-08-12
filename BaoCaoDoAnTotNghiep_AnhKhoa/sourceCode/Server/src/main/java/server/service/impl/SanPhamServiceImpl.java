package server.service.impl;

import java.io.IOException;
import java.io.OutputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.commons.io.FilenameUtils;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import server.converter.SanPhamConverter;
import server.dto.CpuDTO;
import server.dto.MainboardDTO;
import server.dto.NguonMayTinhDTO;
import server.dto.OCungDTO;
import server.dto.RamDTO;
import server.dto.SanPhamDTO;
import server.dto.SanPhamPagingDTO;
import server.dto.SanPhamPostDTO;
import server.dto.VgaDTO;
import server.entity.NhaCungCap;
import server.entity.SanPham;
import server.entity.sanpham_extend.CPU;
import server.entity.sanpham_extend.Mainboard;
import server.entity.sanpham_extend.NguonMayTinh;
import server.entity.sanpham_extend.OCung;
import server.entity.sanpham_extend.RAM;
import server.entity.sanpham_extend.VGA;
import server.repository.SanPhamRepository;
import server.service.SanPhamService;

@Service
public class SanPhamServiceImpl implements SanPhamService{

	private static final Path CURRENT_FOLDER = Paths.get(System.getProperty("user.dir"));
	private static final String JPEG_FILE_PREFIX = "product";
	
	@Autowired
	private SanPhamRepository sanPhamRepository;
	@Autowired
	private SanPhamConverter sanPhamConverter;
	@Autowired
	private ModelMapper modelMapper;
	
	@Override
	public List<SanPhamDTO> getAllProduct() {
		List<SanPham> dssp = sanPhamRepository.findAllProduct();
		List<SanPhamDTO> dsDTO = new ArrayList<>();
		for (SanPham sanPham : dssp) {
			SanPhamDTO dto = sanPhamConverter.toDto(sanPham);
			dsDTO.add(dto);
		}
		return dsDTO;
	}

	@Override
	public SanPhamPagingDTO getAllProductByPage(int pageNo) {
		List<SanPham> dssp = sanPhamRepository.findAllProductPageable(pageNo, null, null, null);
		List<SanPhamDTO> dsDTO = new ArrayList<>();
		for (SanPham sanPham : dssp) {
			SanPhamDTO dto = sanPhamConverter.toDto(sanPham);
			dsDTO.add(dto);
		}
		
		int totalProduct = sanPhamRepository.findAllProduct().size();
		
		SanPhamPagingDTO pagingDTO = new SanPhamPagingDTO();
		pagingDTO.setContent(dsDTO);
		pagingDTO.setTotalPages(countTotalPage(totalProduct));
		pagingDTO.setPageNo(pageNo);
		return pagingDTO;
	}
	
	private int countTotalPage(int totalProduct) {
		int pageSize = 12;
		int totalPage;
		if (totalProduct%pageSize != 0) {
			totalPage = totalProduct/pageSize + 1;
		}else {
			totalPage = totalProduct/pageSize;
		}
		return totalPage;
	}

	@Override
	public SanPhamDTO getProductById(int id) {
		return sanPhamConverter.toDto(sanPhamRepository.findById(id).orElse(null));
	}

	@Override
	public Object getProductDTO(int id) {
		SanPham sanPhamEntity = sanPhamRepository.findById(id).orElse(null);
		String loaiSP = sanPhamRepository.getProductType(sanPhamEntity.getId());
		Object dto = null;
		switch (loaiSP) {
			case "cpu":
				CpuDTO cpuDTO = modelMapper.map(sanPhamEntity, CpuDTO.class);
				cpuDTO.setLoaiSanPham(loaiSP);
				dto = cpuDTO;
				break;
			case "mainboard":
				MainboardDTO mainboardDTO = modelMapper.map(sanPhamEntity, MainboardDTO.class);
				mainboardDTO.setLoaiSanPham(loaiSP);
				dto = mainboardDTO;
				break;
			case "ram":
				RamDTO ramDTO = modelMapper.map(sanPhamEntity, RamDTO.class);
				ramDTO.setLoaiSanPham(loaiSP);
				dto = ramDTO;
				break;
			case "ocung":
				OCungDTO oCungDTO = modelMapper.map(sanPhamEntity, OCungDTO.class);
				oCungDTO.setLoaiSanPham(loaiSP);
				dto = oCungDTO;
				break;
			case "vga":
				VgaDTO vgaDTO = modelMapper.map(sanPhamEntity, VgaDTO.class);
				vgaDTO.setLoaiSanPham(loaiSP);
				dto = vgaDTO;
				break;
			case "nguonmaytinh":
				NguonMayTinhDTO nguonMayTinhDTO = modelMapper.map(sanPhamEntity, NguonMayTinhDTO.class);
				nguonMayTinhDTO.setLoaiSanPham(loaiSP);
				dto = nguonMayTinhDTO;
				break;
			default:
				break;
		}
		return dto;
	}

	@Override
	public void deleteProduct(int id) {
		SanPham sp = sanPhamRepository.getById(id);
		sp.setEnable(false);
		sanPhamRepository.save(sp);
	}

	@Override
	public String uploadImage(MultipartFile image) throws IOException {
		String name = image.getOriginalFilename();
	    System.out.println("File name: "+name);
	      
	    Path staticPath = Paths.get("static");
	    Path imagePath = Paths.get("images");
	    String fileSuffix = "." + FilenameUtils.getExtension(image.getOriginalFilename());
	      
	    if (!Files.exists(CURRENT_FOLDER.resolve(staticPath).resolve(imagePath))) {
            Files.createDirectories(CURRENT_FOLDER.resolve(staticPath).resolve(imagePath));
	    }
       
	    String timeStamp = new SimpleDateFormat("yyyyMMddHHmmss")
                .format(new Date());
	    String imageFileName = JPEG_FILE_PREFIX + timeStamp + fileSuffix;
	    System.err.println(imageFileName);
        
	    Path file = CURRENT_FOLDER.resolve(staticPath)
                .resolve(imagePath).resolve(imageFileName);
	    try (OutputStream os = Files.newOutputStream(file)) {
	    	os.write(image.getBytes());
	    }
	    
	    System.err.println(imagePath.resolve(imageFileName).toString());
	    return imagePath.resolve(imageFileName).toString();
	}

	@Override
	public SanPhamPagingDTO getProductsByType(String category, int pageNo, String type, String sort) {
		List<SanPham> dssp = sanPhamRepository.findAllProductPageable(pageNo, category, type, sort);
		List<SanPhamDTO> dsDTO = new ArrayList<>();
		for (SanPham sanPham : dssp) {
			SanPhamDTO dto = sanPhamConverter.toDto(sanPham);
//			System.err.println(dto);
			dsDTO.add(dto);
		}
		int totalProduct = sanPhamRepository.findAllProductByCategory(category).size();
		
		SanPhamPagingDTO pagingDTO = new SanPhamPagingDTO();
		pagingDTO.setContent(dsDTO);
		pagingDTO.setTotalPages(countTotalPage(totalProduct));
		pagingDTO.setPageNo(pageNo);
		return pagingDTO;
	}

	@Override
	public SanPhamPagingDTO searchProductByName(String keyword, int pageNo) {
		List<SanPham> dssp = sanPhamRepository.searchProductByNamePageable(keyword, pageNo);
		List<SanPhamDTO> dsDTO = new ArrayList<>();
		for (SanPham sanPham : dssp) {
			SanPhamDTO dto = sanPhamConverter.toDto(sanPham);
			dsDTO.add(dto);
		}
		int totalProduct = sanPhamRepository.findAllProductByName(keyword).size();
		
		SanPhamPagingDTO pagingDTO = new SanPhamPagingDTO();
		pagingDTO.setContent(dsDTO);
		pagingDTO.setTotalPages(countTotalPage(totalProduct));
		pagingDTO.setPageNo(pageNo);
		return pagingDTO;
	}

	@Override
	public void addProduct(SanPhamPostDTO postDto) {
		String loaisp = postDto.getLoaiSanPham();
		switch (loaisp) {
		case "cpu":
			CPU cpu = modelMapper.map(postDto, CPU.class);
			cpu.setEnable(true);
			cpu.setNhaCungCap(new NhaCungCap(postDto.getNhaCungCapId()));
			sanPhamRepository.save(cpu);
			break;
		case "mainboard":
			Mainboard mainboard = modelMapper.map(postDto, Mainboard.class);
			mainboard.setEnable(true);
			mainboard.setNhaCungCap(new NhaCungCap(postDto.getNhaCungCapId()));
			sanPhamRepository.save(mainboard);
			break;
		case "ram":
			RAM ram = modelMapper.map(postDto, RAM.class);
			ram.setEnable(true);
			ram.setNhaCungCap(new NhaCungCap(postDto.getNhaCungCapId()));
			sanPhamRepository.save(ram);
			break;
		case "ocung":
			OCung oCung = modelMapper.map(postDto, OCung.class);
			oCung.setEnable(true);
			oCung.setNhaCungCap(new NhaCungCap(postDto.getNhaCungCapId()));
			sanPhamRepository.save(oCung);
			break;
		case "vga":
			VGA vga = modelMapper.map(postDto, VGA.class);
			vga.setEnable(true);
			vga.setNhaCungCap(new NhaCungCap(postDto.getNhaCungCapId()));
			sanPhamRepository.save(vga);
			break;
		case "nguonmaytinh":
			NguonMayTinh nguonMayTinh = modelMapper.map(postDto, NguonMayTinh.class);
			nguonMayTinh.setEnable(true);
			nguonMayTinh.setNhaCungCap(new NhaCungCap(postDto.getNhaCungCapId()));
			sanPhamRepository.save(nguonMayTinh);
			break;
		default:
			break;
		}
	}

	@Override
	public void editProduct(SanPhamPostDTO postDto) {
		Object productDTO = getProductDTO(postDto.getId());
		String loaisp = postDto.getLoaiSanPham();
		switch (loaisp) {
		case "cpu":
			CPU cpu = modelMapper.map(postDto, CPU.class);
			if (!postDto.getUrlHinhAnh().trim().equals("")) {
				cpu.setUrlHinhAnh(postDto.getUrlHinhAnh());
			}else {
				cpu.setUrlHinhAnh(((SanPhamDTO) productDTO).getUrlHinhAnh());
			}
			cpu.setEnable(true);
			cpu.setNhaCungCap(new NhaCungCap(postDto.getNhaCungCapId()));
			System.out.println(cpu);
			sanPhamRepository.save(cpu);
			break;
		case "mainboard":
			Mainboard mainboard = modelMapper.map(postDto, Mainboard.class);
			if (!postDto.getUrlHinhAnh().trim().equals("")) {
				mainboard.setUrlHinhAnh(postDto.getUrlHinhAnh());
			}else {
				mainboard.setUrlHinhAnh(((SanPhamDTO) productDTO).getUrlHinhAnh());
			}
			mainboard.setEnable(true);
			mainboard.setNhaCungCap(new NhaCungCap(postDto.getNhaCungCapId()));
			sanPhamRepository.save(mainboard);
			break;
		case "ram":
			RAM ram = modelMapper.map(postDto, RAM.class);
			if (!postDto.getUrlHinhAnh().trim().equals("")) {
				ram.setUrlHinhAnh(postDto.getUrlHinhAnh());
			}else {
				ram.setUrlHinhAnh(((SanPhamDTO) productDTO).getUrlHinhAnh());
			}
			ram.setEnable(true);
			ram.setNhaCungCap(new NhaCungCap(postDto.getNhaCungCapId()));
			sanPhamRepository.save(ram);
			break;
		case "ocung":
			OCung oCung = modelMapper.map(postDto, OCung.class);
			if (!postDto.getUrlHinhAnh().trim().equals("")) {
				oCung.setUrlHinhAnh(postDto.getUrlHinhAnh());
			}else {
				oCung.setUrlHinhAnh(((SanPhamDTO) productDTO).getUrlHinhAnh());
			}
			oCung.setEnable(true);
			oCung.setNhaCungCap(new NhaCungCap(postDto.getNhaCungCapId()));
			sanPhamRepository.save(oCung);
			break;
		case "vga":
			VGA vga = modelMapper.map(postDto, VGA.class);
			if (!postDto.getUrlHinhAnh().trim().equals("")) {
				vga.setUrlHinhAnh(postDto.getUrlHinhAnh());
			}else {
				vga.setUrlHinhAnh(((SanPhamDTO) productDTO).getUrlHinhAnh());
			}
			vga.setEnable(true);
			vga.setNhaCungCap(new NhaCungCap(postDto.getNhaCungCapId()));
			sanPhamRepository.save(vga);
			break;
		case "nguonmaytinh":
			NguonMayTinh nguonMayTinh = modelMapper.map(postDto, NguonMayTinh.class);
			if (!postDto.getUrlHinhAnh().trim().equals("")) {
				nguonMayTinh.setUrlHinhAnh(postDto.getUrlHinhAnh());
			}else {
				nguonMayTinh.setUrlHinhAnh(((SanPhamDTO) productDTO).getUrlHinhAnh());
			}
			nguonMayTinh.setEnable(true);
			nguonMayTinh.setNhaCungCap(new NhaCungCap(postDto.getNhaCungCapId()));
			sanPhamRepository.save(nguonMayTinh);
			break;
		default:
			break;
		}
	}

}
