'use strict';

class RandomDatePicturesButton extends React.Component {
    constructor(props) {
        super(props);
        this.hideText = 'Hide Random Day\'s Picture/s';
        this.state = {items: [], error: null, isPressed: false, loaded: false};
    }

    render() {
        return new TemplateComponent(this).render();
    }

    showButton() {
        return e(
            'button',
            {
                onClick: () => {
                    this.setState({isPressed: true});
                    fetchData(this, true);
                }
            },
            'Show Random Day\'s Picture/s'
        );
    }
}

const dom2Container = document.querySelector('#random_pictures_button_container');
ReactDOM.render(e(RandomDatePicturesButton), dom2Container);
