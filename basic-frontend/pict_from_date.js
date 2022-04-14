'use strict';

class PicturesFromDateButton extends React.Component {
    constructor(props) {
        super(props);
        this.hideText = 'Hide Picture/s from Date';
        this.state = {items: [], error: null, date: null, isPressed: false, loaded: false};
    }

    render() {
        return new TemplateComponent(this).render();
    }

    showButton() {
        return e('div', {},
            e('div', {},
                e('button',
                    {
                        disabled: !this.state.date || new Date(this.state.date) > new Date(),
                        onClick: () => {
                            this.setState({isPressed: true});
                            fetchData(this);
                        }, key: 'pictFromDate'
                    },
                    'Show Picture/s from Date'
                )
            ),
            e('div', { className: 'date-picker' },
                e('input',
                    {
                        placeholder: 'YYMMDD',
                        type: 'date',
                        key: 'input',
                        min: '2011-04-20',
                        max: new Date().toISOString().slice(0, 10),
                        onChange: e => this.setState({date: e.target.value})
                    }
                )
            )
        );
    }
}

const dom3Container = document.querySelector('#pictures_from_date_button_container');
ReactDOM.render(e(PicturesFromDateButton), dom3Container);
