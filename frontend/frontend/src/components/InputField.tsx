import React, { ChangeEvent } from 'react';

interface InputFieldProps {
    label: string;
    type: string;
    name: string;
    value: string;
    onChange: (event: ChangeEvent<HTMLInputElement>) => void;
    placeholder?: string;
    error?: string;
}

const InputField: React.FC<InputFieldProps> = ({
   label,
   type,
   name,
   value,
   onChange,
   placeholder,
   error
}) => {
    return (
        <div className="form-group">
            <label className="font-semibold capitalize"
                   style={{
                       color: 'white',
                   }}>
                {label}
            </label>
            <input
                type={type}
                name={name}
                value={value}
                onChange={onChange}
                placeholder={placeholder}
                className={`form-control ${error ? 'is-invalid' : ''}`}

            />
            {error && <span className="text-danger">{error}</span>}
        </div>
    );
};

export default InputField;
