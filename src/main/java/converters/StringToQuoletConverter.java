
package converters;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import repositories.QuoletRepository;
import domain.Quolet;

@Component
@Transactional
public class StringToQuoletConverter implements Converter<String, Quolet> {

	@Autowired
	QuoletRepository	quoletRepository;


	@Override
	public Quolet convert(final String s) {
		Quolet result;
		int id;

		try {
			if (StringUtils.isEmpty(s))
				result = null;
			else {
				id = Integer.valueOf(s);
				result = this.quoletRepository.findOne(id);
			}
		} catch (final Throwable oops) {
			throw new IllegalArgumentException(oops);
		}

		return result;
	}
}
