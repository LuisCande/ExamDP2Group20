
package converters;

import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import domain.Quolet;

@Component
@Transactional
public class QuoletToStringConverter implements Converter<Quolet, String> {

	@Override
	public String convert(final Quolet a) {
		String result;

		if (a == null)
			result = null;
		else
			result = String.valueOf(a.getId());

		return result;
	}
}
