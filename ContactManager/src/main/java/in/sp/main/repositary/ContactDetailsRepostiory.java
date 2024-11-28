package in.sp.main.repositary;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import in.sp.main.entity.ContactDetails;

public interface ContactDetailsRepostiory extends JpaRepository<ContactDetails, Integer>{
	public List<ContactDetails> findByUserId(int userId);
	public ContactDetails findByEmail(String email);
	public ContactDetails findById(int id);
	public void deleteById(int id);
}
